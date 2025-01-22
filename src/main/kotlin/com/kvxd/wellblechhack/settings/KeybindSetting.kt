package com.kvxd.wellblechhack.settings

import com.kvxd.wellblechhack.setting.Setting
import net.minecraft.nbt.NbtCompound

class KeybindSetting(name: String, display: String = name, default: Int, value: Int) : Setting<Int>(
    name, display, default,
    value
) {

    override fun load(tag: NbtCompound) {
        value = tag.getInt("value")
    }

    override fun save(tag: NbtCompound) {
        tag.putInt("value", value)
    }

}