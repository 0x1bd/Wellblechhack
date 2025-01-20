//https://github.com/MeteorDevelopment/meteor-client/blob/master/src/main/java/meteordevelopment/meteorclient/utils/player/SlotUtils.java
package com.kvxd.wellblechhack.util

import com.kvxd.wellblechhack.mc
import com.kvxd.wellblechhack.player
import net.minecraft.screen.*

object SlotUtils {
    const val HOTBAR_START: Int = 0
    const val HOTBAR_END: Int = 8

    const val OFFHAND: Int = 45

    const val MAIN_START: Int = 9
    const val MAIN_END: Int = 35

    const val ARMOR_START: Int = 36
    const val ARMOR_END: Int = 39

    fun indexToId(i: Int): Int {
        if (mc.player == null) return -1

        return when (val handler = player!!.currentScreenHandler) {
            is PlayerScreenHandler -> survivalInventory(i)
            is GenericContainerScreenHandler -> genericContainer(i, handler.rows)
            is CraftingScreenHandler -> craftingTable(i)
            is FurnaceScreenHandler, is BlastFurnaceScreenHandler, is SmokerScreenHandler -> furnace(i)
            is Generic3x3ContainerScreenHandler -> generic3x3(i)
            is EnchantmentScreenHandler -> enchantmentTable(i)
            is BrewingStandScreenHandler -> brewingStand(i)
            is MerchantScreenHandler -> villager(i)
            is BeaconScreenHandler -> beacon(i)
            is AnvilScreenHandler -> anvil(i)
            is HopperScreenHandler -> hopper(i)
            is ShulkerBoxScreenHandler -> genericContainer(i, 3)
            is CartographyTableScreenHandler -> cartographyTable(i)
            is GrindstoneScreenHandler -> grindstone(i)
            is LecternScreenHandler -> lectern()
            is LoomScreenHandler -> loom(i)
            is StonecutterScreenHandler -> stonecutter(i)
            else -> -1
        }
    }

    private fun survivalInventory(i: Int): Int = when {
        isHotbar(i) -> 36 + i
        isArmor(i) -> 5 + (i - 36)
        else -> i
    }

    private fun genericContainer(i: Int, rows: Int): Int = when {
        isHotbar(i) -> (rows + 3) * 9 + i
        isMain(i) -> rows * 9 + (i - 9)
        else -> -1
    }

    private fun craftingTable(i: Int): Int = when {
        isHotbar(i) -> 37 + i
        isMain(i) -> i + 1
        else -> -1
    }

    private fun furnace(i: Int): Int = when {
        isHotbar(i) -> 30 + i
        isMain(i) -> 3 + (i - 9)
        else -> -1
    }

    private fun generic3x3(i: Int): Int = when {
        isHotbar(i) -> 36 + i
        isMain(i) -> i
        else -> -1
    }

    private fun enchantmentTable(i: Int): Int = when {
        isHotbar(i) -> 29 + i
        isMain(i) -> 2 + (i - 9)
        else -> -1
    }

    private fun brewingStand(i: Int): Int = when {
        isHotbar(i) -> 32 + i
        isMain(i) -> 5 + (i - 9)
        else -> -1
    }

    private fun villager(i: Int): Int = when {
        isHotbar(i) -> 30 + i
        isMain(i) -> 3 + (i - 9)
        else -> -1
    }

    private fun beacon(i: Int): Int = when {
        isHotbar(i) -> 28 + i
        isMain(i) -> 1 + (i - 9)
        else -> -1
    }

    private fun anvil(i: Int): Int = when {
        isHotbar(i) -> 30 + i
        isMain(i) -> 3 + (i - 9)
        else -> -1
    }

    private fun hopper(i: Int): Int = when {
        isHotbar(i) -> 32 + i
        isMain(i) -> 5 + (i - 9)
        else -> -1
    }

    private fun cartographyTable(i: Int): Int = when {
        isHotbar(i) -> 30 + i
        isMain(i) -> 3 + (i - 9)
        else -> -1
    }

    private fun grindstone(i: Int): Int = when {
        isHotbar(i) -> 30 + i
        isMain(i) -> 3 + (i - 9)
        else -> -1
    }

    private fun lectern(): Int = -1

    private fun loom(i: Int): Int = when {
        isHotbar(i) -> 31 + i
        isMain(i) -> 4 + (i - 9)
        else -> -1
    }

    private fun stonecutter(i: Int): Int = when {
        isHotbar(i) -> 29 + i
        isMain(i) -> 2 + (i - 9)
        else -> -1
    }

    // Utils
    fun isHotbar(i: Int): Boolean = i in HOTBAR_START..HOTBAR_END

    fun isMain(i: Int): Boolean = i in MAIN_START..MAIN_END

    fun isArmor(i: Int): Boolean = i in ARMOR_START..ARMOR_END
}