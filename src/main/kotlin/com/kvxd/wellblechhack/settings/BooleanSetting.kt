package com.kvxd.wellblechhack.settings

import com.kvxd.wellblechhack.setting.Setting
import net.minecraft.nbt.NbtCompound

class BooleanSetting(name: String, display: String = name, default: Boolean, value: Boolean) : Setting<Boolean>(
    name, display, default,
    value
) {

    override fun load(tag: NbtCompound) {
        val byteValue = tag.getByte("value")

        value = when (byteValue) {
            1.toByte() -> true
            else -> false
        }
    }

    override fun save(tag: NbtCompound) {
        tag.putBoolean("value", value)
    }

}