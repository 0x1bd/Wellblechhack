package com.kvxd.wellblechhack.util

import com.kvxd.wellblechhack.interaction
import com.kvxd.wellblechhack.network
import com.kvxd.wellblechhack.player
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.screen.slot.SlotActionType
import net.minecraft.util.Hand

object InventoryUtils {

    enum class Where {
        INVENTORY,
        CONTAINER
    }

    fun moveItem(source: Int, dest: Int, simulate: Boolean = false, replaceAction: () -> Unit = {}) {
        if (!player!!.inventory.getStack(dest).isEmpty)
            replaceAction()

        clickSlot(source)
        clickSlot(dest)

        if (simulate)
            network!!.sendPacket(CloseHandledScreenC2SPacket(player.playerScreenHandler.syncId))
    }

    fun findItem(predicate: (stack: ItemStack) -> Boolean, where: Where = Where.INVENTORY): Pair<Int, ItemStack>? {
        return player!!.inventory.main
            .withIndex()
            .find { predicate(it.value) }
            ?.let { Pair(it.index, it.value) }
    }

    fun dropStack(slot: Int, swing: Boolean = true) {
        if (swing)
            player!!.swingHand(Hand.MAIN_HAND)

        clickSlot(
            slot,
            actionType = SlotActionType.THROW
        )
    }

    fun clickSlot(slot: Int, button: Int = 0, actionType: SlotActionType = SlotActionType.PICKUP, where: Where = Where.INVENTORY) {
        interaction!!.clickSlot(
            if (where == Where.INVENTORY) player!!.currentScreenHandler.syncId else player!!.currentScreenHandler.syncId,
            if (where == Where.INVENTORY) slot else SlotUtils.indexToId(slot),
            button,
            actionType,
            player
        )
    }

}