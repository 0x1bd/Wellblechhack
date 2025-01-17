package com.kvxd.wellblechhack
import org.slf4j.LoggerFactory

/**
 * Main object for the Wellblechhack
 */
object Wellblechhack {

    private val logger = LoggerFactory.getLogger("Wellblechhack")

    /**
     * Initializes the Wellblechhack. Not using fabric's client entrypoint because it initializes too early.
     * @see com.kvxd.wellblechhack.mixin.MinecraftClientMixin.init
     */
    fun initialize() {
        logger.info("Initializing")
    }

}
