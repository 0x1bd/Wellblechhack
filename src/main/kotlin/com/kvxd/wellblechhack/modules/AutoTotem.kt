package com.kvxd.wellblechhack.modules

import com.kvxd.wellblechhack.events.PacketReceivedEvent
import com.kvxd.wellblechhack.module.Category
import com.kvxd.wellblechhack.module.Module
import com.kvxd.wellblechhack.util.InventoryUtils
import com.kvxd.wellblechhack.util.SlotUtils
import net.minecraft.entity.EntityStatuses
import net.minecraft.item.Items
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket

object AutoTotem : Module("Auto-Totem", "Automatically equips a new totem if the current one pops.", Category.PLAYER) {

    val handler = eventBus.handler(
        PacketReceivedEvent::class,
        filter = { it.packet is EntityStatusS2CPacket && it.packet.status == EntityStatuses.USE_TOTEM_OF_UNDYING }) {

        val pair = InventoryUtils.findItem({ stack -> stack.item == Items.TOTEM_OF_UNDYING })

        pair?.let {
            InventoryUtils.moveItem(pair.first, SlotUtils.OFFHAND)
        }
    }

}