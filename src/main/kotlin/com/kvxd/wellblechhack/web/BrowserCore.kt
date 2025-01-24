package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.eventBus
import com.kvxd.wellblechhack.events.BrowserReadyEvent
import com.kvxd.wellblechhack.logger
import com.mojang.blaze3d.systems.RenderSystem
import net.ccbluex.liquidbounce.mcef.MCEF
import kotlin.concurrent.thread

object BrowserCore {

    private val rootStorage by lazy {
        Wellblechhack.ROOT_FILE.resolve("mcef")
    }

    private val dependencyStorage get() = rootStorage.resolve("libraries")
    private val temporaryCache get() = rootStorage.resolve("libraries")

    val isReady: Boolean
        get() = MCEF.INSTANCE.isInitialized

    fun prepareEnvironment(onCompletion: () -> Unit) {
        if (MCEF.INSTANCE.isInitialized) return
        logger.info("Preparing environment for mcef")

        configurePlatform().run {
            fetchResourcesIfNeeded(
                onSuccess = {
                    RenderSystem.recordRenderCall(onCompletion)
                    eventBus.post(BrowserReadyEvent())
                },
            )
        }
    }

    private fun configurePlatform() = apply {
        MCEF.INSTANCE.settings.apply {
            userAgent =
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36"
            librariesDirectory = dependencyStorage
            cacheDirectory = temporaryCache.resolve(System.nanoTime().toString(36)).apply {
                deleteOnExit()
            }
        }
    }

    private fun fetchResourcesIfNeeded(onSuccess: () -> Unit) {
        val resourceLoader = MCEF.INSTANCE.newResourceManager()

        if (!resourceLoader.requiresDownload()) {
            runCatching(onSuccess).onFailure(Wellblechhack::fatal)
            return
        }

        thread(name = "mcef-dl") {
            runCatching {
                logger.info("Downloading JCEF")

                resourceLoader.downloadJcef()

                onSuccess()
            }.onFailure(Wellblechhack::fatal)
        }
    }

    fun seek() {
        if (!isReady) return

        MCEF.INSTANCE.app.handle.N_DoMessageLoopWork()
    }

    fun shutdown() {
        if (isReady)
            MCEF.INSTANCE.shutdown()
    }
}