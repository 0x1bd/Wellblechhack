package com.kvxd.wellblechhack

import net.minecraft.client.MinecraftClient

val mc
    get() = MinecraftClient.getInstance()

val player = mc.player
val world = mc.world
val interaction = mc.interactionManager
val network = mc.networkHandler