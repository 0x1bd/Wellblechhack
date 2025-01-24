package com.kvxd.wellblechhack

import net.minecraft.client.MinecraftClient
import org.slf4j.LoggerFactory

val mc
    get() = MinecraftClient.getInstance()

val player = mc.player
val world = mc.world
val interaction = mc.interactionManager
val network = mc.networkHandler

val logger = LoggerFactory.getLogger(Wellblechhack.NAME)
val eventBus = Wellblechhack.EVENT_BUS