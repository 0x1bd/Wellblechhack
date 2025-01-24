package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.logger
import com.kvxd.wellblechhack.util.FileHierarchy
import net.ccbluex.liquidbounce.mcef.MCEF
import kotlin.concurrent.thread

private const val CACHE_CLEANUP_THRESHOLD = 1000 * 60 * 60 * 24 * 7 // 7 days

object WebViewEnvironment {

    object Storage : FileHierarchy(Wellblechhack.ROOT_FILE) {
        private val mcef by dir("mcef")
        val libs by mcef.dir("libs")
        val cache by mcef.dir("cache")
    }

    fun create() {
        if (!MCEF.INSTANCE.isInitialized) {
            MCEF.INSTANCE.settings.apply {
                userAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:134.0) Gecko/20100101 Firefox/134.0"

                cacheDirectory = Storage.cache.resolve(System.currentTimeMillis().toString(16)).apply {
                    deleteOnExit()
                }
                librariesDirectory = Storage.libs
            }

            val resourceManager = MCEF.INSTANCE.newResourceManager()

            if (resourceManager.requiresDownload()) {
                thread(name = "mcef-dl") {
                    runCatching {
                        resourceManager.downloadJcef()
                        initializeMCEF()
                    }.onFailure(Wellblechhack::fatal)
                }
            } else {
                initializeMCEF()
            }
        }

        thread(name = "mcef-cleanup", block = this::cleanup)
    }

    fun cleanup() = runCatching {
        if (!Storage.cache.exists()) return@runCatching
        Storage.cache.listFiles()?.filter { file ->
            file.isDirectory && System.currentTimeMillis() - file.lastModified() > CACHE_CLEANUP_THRESHOLD
        }?.sumOf { file ->
            try {
                val fileSize = file.walkTopDown().sumOf { uFile -> uFile.length() }
                file.deleteRecursively()
                fileSize
            } catch (e: Exception) {
                logger.error("Failed to clean up old cache directory", e)
                0
            }
        }
    }.onFailure {
        logger.error("Failed to clean up old JCEF cache directories", it)
    }.onSuccess {
        logger.info("Cleaned up JCEF cache directories")
    }

    private fun initializeMCEF() = runCatching {
        if (!MCEF.INSTANCE.isInitialized) MCEF.INSTANCE.initialize()
    }.onFailure(Wellblechhack::fatal)

    fun peek() {
        if (MCEF.INSTANCE.isInitialized) MCEF.INSTANCE.app.handle.N_DoMessageLoopWork()
    }

    fun shutdown() {
        MCEF.INSTANCE.shutdown()
        Storage.cache.deleteRecursively()
    }

}