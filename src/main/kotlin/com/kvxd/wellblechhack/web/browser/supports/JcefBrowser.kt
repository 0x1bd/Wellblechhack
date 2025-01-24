package com.kvxd.wellblechhack.web.browser.supports

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.logger
import com.mojang.blaze3d.systems.RenderSystem
import com.kvxd.wellblechhack.web.browser.supports.tab.JcefTab
import com.kvxd.wellblechhack.web.browser.supports.tab.TabPosition
import net.ccbluex.liquidbounce.mcef.MCEF
import net.fabricmc.loader.api.FabricLoader
import kotlin.concurrent.thread

/**
 * The time threshold for cleaning up old cache directories.
 */
private const val CACHE_CLEANUP_THRESHOLD = 1000 * 60 * 60 * 24 * 7 // 7 days

/**
 * Uses a modified fork of the JCEF library browser backend made for Minecraft.
 * This browser backend is based on Chromium and is the most advanced browser backend.
 * JCEF is available through the MCEF library, which provides a Minecraft compatible version of JCEF.
 *
 * @see <a href="https://github.com/CCBlueX/java-cef/">JCEF</a>
 * @see <a href="https://github.com/CCBlueX/mcef/">MCEF</a>
 *
 * @author 1zuna <marco@ccbluex.net>
 */
@Suppress("TooManyFunctions")
class JcefBrowser : IBrowser {

    private val mcefFolder = Wellblechhack.ROOT_FILE.resolve("mcef")
    private val librariesFolder = mcefFolder.resolve("libraries")
    private val cacheFolder = mcefFolder.resolve("cache")
    private val tabs = mutableListOf<JcefTab>()

    override fun makeDependenciesAvailable(whenAvailable: () -> Unit) {
        if (!MCEF.INSTANCE.isInitialized) {
            MCEF.INSTANCE.settings.apply {
                // Uses a natural user agent to prevent websites from blocking the browser
                userAgent = "Somemod/0.42.42" +
                    " (42, branch, " +
                    "${if (FabricLoader.getInstance().isDevelopmentEnvironment) "dev" else "release"}, ${System.getProperty("os.name")})"
                cacheDirectory = cacheFolder.resolve(System.currentTimeMillis().toString(16)).apply {
                    deleteOnExit()
                }
                librariesDirectory = librariesFolder
            }

            val resourceManager = MCEF.INSTANCE.newResourceManager()

            if (resourceManager.requiresDownload()) {
                thread(name = "mcef-downloader") {
                    runCatching {
                        resourceManager.downloadJcef()
                        RenderSystem.recordRenderCall(whenAvailable)
                    }.onFailure(Wellblechhack::fatal)
                }
            } else {
                whenAvailable()
            }
        }

        // Clean up old cache directories
        thread(name = "mcef-cache-cleanup", block = this::cleanup)
    }

    /**
     * Cleans up old cache directories.
     *
     * TODO: Check if we have an active PID using the cache directory, if so, check if the LiquidBounce
     *   process attached to the JCEF PID is still running or not. If not, we could kill the JCEF process
     *   and clean up the cache directory.
     */
    fun cleanup() {
        if (cacheFolder.exists()) {
            runCatching {
                cacheFolder.listFiles()
                    ?.filter { file ->
                        file.isDirectory && System.currentTimeMillis() - file.lastModified() > CACHE_CLEANUP_THRESHOLD
                    }
                    ?.sumOf { file ->
                        try {
                            val fileSize = file.walkTopDown().sumOf { uFile -> uFile.length() }
                            file.deleteRecursively()
                            fileSize
                        } catch (e: Exception) {
                            logger.error("Failed to clean up old cache directory", e)
                            0
                        }
                    } ?: 0
            }.onFailure {
                // Not a big deal, not fatal.
                logger.error("Failed to clean up old JCEF cache directories", it)
            }.onSuccess { size ->
                if (size > 0) {
                    logger.info("Cleaned up JCEF cache directories")
                }
            }
        }
    }

    override fun initBrowserBackend() {
        if (!MCEF.INSTANCE.isInitialized) {
            MCEF.INSTANCE.initialize()
        }
    }

    override fun shutdownBrowserBackend() {
        MCEF.INSTANCE.shutdown()
        MCEF.INSTANCE.settings.cacheDirectory?.deleteRecursively()
    }

    override fun isInitialized() = MCEF.INSTANCE.isInitialized

    override fun createTab(url: String, position: TabPosition, frameRate: Int) =
        JcefTab(this, url, position, frameRate) { false }.apply(::addTab)

    override fun createInputAwareTab(url: String, position: TabPosition, frameRate: Int, takesInput: () -> Boolean) =
        JcefTab(this, url, position, frameRate, takesInput = takesInput).apply(::addTab)

    override fun getTabs(): List<JcefTab> = tabs

    private fun addTab(tab: JcefTab) {
        tabs.sortedInsert(tab, JcefTab::preferOnTop)
    }

    internal fun removeTab(tab: JcefTab) {
        tabs.remove(tab)
    }

    override fun drawGlobally() {
        if (MCEF.INSTANCE.isInitialized) {
            try {
                MCEF.INSTANCE.app.handle.N_DoMessageLoopWork()
            } catch (e: Exception) {
                logger.error("Failed to draw browser globally", e)
            }
        }
    }

}

inline fun <T, K : Comparable<K>> MutableList<T>.sortedInsert(item: T, crossinline selector: (T) -> K?) {
    val insertIndex = binarySearchBy(selector(item), selector = selector).let {
        if (it >= 0) it else it.inv()
    }

    add(insertIndex, item)
}