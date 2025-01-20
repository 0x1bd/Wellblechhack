package com.kvxd.wellblechhack.modules

import com.kvxd.wellblechhack.events.PacketReceivedEvent
import com.kvxd.wellblechhack.module.Module
import net.minecraft.entity.EntityStatuses
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket

object AutoTotem : Module("Auto-Totem", "Automatically equips a new totem if the current one pops.") {

    val handler = eventBus.handler(
        PacketReceivedEvent::class,
        filter = { it.packet is EntityStatusS2CPacket && it.packet.status == EntityStatuses.USE_TOTEM_OF_UNDYING }) {
        println("Totem popped.")
    }

}