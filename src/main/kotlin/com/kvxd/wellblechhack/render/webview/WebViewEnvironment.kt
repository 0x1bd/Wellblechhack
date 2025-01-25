package com.kvxd.wellblechhack.render.webview

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.logger
import com.kvxd.wellblechhack.util.FileHierarchy
import net.ccbluex.liquidbounce.mcef.MCEF
import kotlin.concurrent.thread

private const val CACHE_CLEANUP_THRESHOLD = 1000 * 60 * 60 * 24 * 7

object WebViewEnvironment {

    val views = mutableListOf<WebView>()

    fun addView(view: WebView): WebView {
        views.add(view)
        return view
    }

    fun removeView(view: WebView): WebView {
        view.close()
        views.remove(view)
        return view
    }

    object Storage : FileHierarchy(Wellblechhack.ROOT_FILE) {
        val mcef by dir("mcef")
        val libs by mcef.dir("libs")
        val cache by mcef.dir("cache")
    }

    fun setupEnvironment(onFinish: () -> Unit = ::initializeMCEF) {
        if (!MCEF.INSTANCE.isInitialized) {
            logger.info("Setting up MCEF")
            configurePlatform().run {
                val resourceManager = MCEF.INSTANCE.newResourceManager()

                if (resourceManager.requiresDownload())
                    resourceManager.downloadJcef()
            }
        }

        onFinish()

        WebViewRenderer

        thread(name = "mcef-cleanup-cache", block = ::cleanup)
    }

    private fun initializeMCEF() {
        if (!MCEF.INSTANCE.isInitialized)
            MCEF.INSTANCE.initialize()
    }

    private fun configurePlatform() = apply {
        MCEF.INSTANCE.settings.apply {
            userAgent =
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36"

            librariesDirectory = Storage.libs
            cacheDirectory = Storage.cache.resolve(System.currentTimeMillis().toString(16)).apply {
                deleteOnExit()
            }
        }
    }

    private fun cleanup() {
        if (!Storage.cache.exists()) return

        runCatching {
            Storage.cache.listFiles()
                ?.filter { file ->
                    file.isDirectory && System.currentTimeMillis() - file.lastModified() > CACHE_CLEANUP_THRESHOLD
                }?.sumOf { file ->
                    try {
                        file.deleteRecursively()
                        1L
                    } catch (e: Exception) {
                        0
                    }
                } ?: -1
        }.onFailure {
            logger.error("Failed to clean up jcef cache")
        }.onSuccess { amount ->
            logger.info("Cleaned up $amount jcef cache files")
        }
    }

    fun shutdown() {
        if (MCEF.INSTANCE.isInitialized) {
            MCEF.INSTANCE.shutdown()
            Storage.cache.deleteRecursively()
        }
    }

    fun renderWebView() {
        if (MCEF.INSTANCE.isInitialized)
            MCEF.INSTANCE.app.handle.N_DoMessageLoopWork()
    }

}