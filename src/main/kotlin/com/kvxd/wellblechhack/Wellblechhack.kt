package com.kvxd.wellblechhack

import com.kvxd.eventbus.EventBus
import com.kvxd.wellblechhack.module.ModuleSystem
import com.kvxd.wellblechhack.render.webview.WebViewEnvironment
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import kotlin.system.exitProcess

/**
 * Main object for the Wellblechhack
 */
object Wellblechhack {

    const val NAME = "Wellblechhack"
    const val MOD_ID = "wellblechhack"

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

        WebViewEnvironment.setupEnvironment()
        ModuleSystem.initialize()

        ClientLifecycleEvents.CLIENT_STOPPING.register {
            WebViewEnvironment.shutdown()
            ModuleSystem.save()
        }
    }

    fun fatal(throwable: Throwable) {
        logger.error("Fatal error! {}", throwable.message)
        exitProcess(-1)
    }

}
