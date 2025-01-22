package com.kvxd.wellblechhack

import com.kvxd.eventbus.EventBus
import com.kvxd.wellblechhack.module.ModuleSystem
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Main object for the Wellblechhack
 */
object Wellblechhack {

    val NAME = "Wellblechhack"
    val MOD_ID = "wellblechhack"

    val EVENT_BUS = EventBus.create()

    private val logger = LoggerFactory.getLogger(NAME)

    val ROOT_FILE = File(FabricLoader.getInstance().gameDir.toFile(), MOD_ID)

    /**
     * Initializes the Wellblechhack. Not using fabric's client entrypoint because it initializes too early.
     * @see com.kvxd.wellblechhack.mixin.MinecraftClientMixin.init
     */
    fun initialize() {
        logger.info("Initializing")

        if (!ROOT_FILE.exists())
            ROOT_FILE.mkdir()

        ModuleSystem.initialize()

        ClientLifecycleEvents.CLIENT_STOPPING.register {
            ModuleSystem.save()
        }
    }

}
