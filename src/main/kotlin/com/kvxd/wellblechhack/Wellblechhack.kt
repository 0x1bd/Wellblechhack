package com.kvxd.wellblechhack

import com.kvxd.eventbus.EventBus
import com.kvxd.wellblechhack.module.ModuleSystem
import com.kvxd.wellblechhack.web.BrowserCore
import com.kvxd.wellblechhack.web.api.runApiServer
import net.ccbluex.liquidbounce.mcef.MCEF
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

        BrowserCore.prepareEnvironment {
            runCatching {
                MCEF.INSTANCE.initialize()
            }.onFailure(::fatal)
        }

        thread(name = "api-server") {
            runApiServer()
        }

        ClientLifecycleEvents.CLIENT_STOPPING.register {
            BrowserCore.shutdown()
            ModuleSystem.save()
        }
    }

    fun fatal(throwable: Throwable) {
        logger.error("Fatal error! {}", throwable.message)
        exitProcess(-1)
    }

}
