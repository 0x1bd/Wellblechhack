package com.kvxd.wellblechhack.module

import com.kvxd.wellblechhack.setting.Setting
import com.kvxd.wellblechhack.util.Persistable
import net.minecraft.nbt.NbtCompound

abstract class Module(val name: String, val description: String): Persistable<Module> {

    private val settings = mutableSetOf<Setting<*>>()

    override fun load(tag: NbtCompound): Module? {
        if (tag.getString("name") == name)
            return this
        return null
    }

    override fun save(tag: NbtCompound) {
        tag.apply {
            putString("name", name)

            settings.forEach { setting: Setting<*> ->
                val settingsTag = NbtCompound()
                settingsTag.put("value", setting.toTag())
                tag.put(setting.name, settingsTag)
            }
        }
    }

}