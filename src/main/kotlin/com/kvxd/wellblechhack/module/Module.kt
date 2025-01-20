package com.kvxd.wellblechhack.module

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.setting.Setting
import com.kvxd.wellblechhack.settings.BooleanSetting
import com.kvxd.wellblechhack.util.Persistable
import net.minecraft.nbt.NbtCompound

abstract class Module(val name: String, val description: String) : Persistable {

    private val settings = mutableSetOf<Setting<*>>()

    val enabled by boolean("enabled", "Is the module enabled.")

    protected val eventBus = Wellblechhack.EVENT_BUS.forward { enabled }

    override fun load(tag: NbtCompound) {
        settings.forEach { setting ->
            val settingTag = tag.get(setting.name) as NbtCompound
            setting.load(settingTag)
        }
    }

    override fun save(tag: NbtCompound) {
        val moduleTag = NbtCompound()
        settings.forEach { setting ->
            val settingTag = NbtCompound()
            setting.save(settingTag)
            moduleTag.put(setting.name, settingTag)
        }
        tag.put(name, moduleTag)
    }

    private fun boolean(name: String, description: String, display: String = name, default: Boolean = false) =
        BooleanSetting(name, display, default, default)
            .also { settings.add(it) }
}