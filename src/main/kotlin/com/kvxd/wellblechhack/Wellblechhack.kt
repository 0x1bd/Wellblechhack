package com.kvxd.wellblechhack

import com.kvxd.eventbus.EventBus
import com.kvxd.wellblechhack.module.ModuleSystem
import com.kvxd.wellblechhack.web.browser.BrowserManager
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import kotlin.concurrent.thread
import kotlin.system.exitProcess

/**
 * Main object for the Wellblechhack
 */
object Wellblechhack {

    val NAME = "Wellblechhack"
    val MOD_ID = "wellblechhack"

    val EVENT_BUS = EventBus.create()

    val ROOT_FILE = File(FabricLoader.getInstance().gameDir.toFile(), MOD_ID)

    /**
     * Initializes the Wellblechhack. Not using fabric's client entrypoint because it initializes too early.
     * @see com.kvxd.wellblechhack.mixin.MinecraftClientMixin.onInit
     */
    fun initialize() {
        logger.info("Initializing")

        if (!ROOT_FILE.exists())
            ROOT_FILE.mkdir()

        ModuleSystem.initialize()

        BrowserManager.initBrowser()

        ClientLifecycleEvents.CLIENT_STOPPING.register {
            BrowserManager.shutdownBrowser()
            ModuleSystem.save()
        }
    }

    fun fatal(throwable: Throwable) {
        logger.error("Fatal error! {}", throwable.message)
        exitProcess(-1)
    }

}
