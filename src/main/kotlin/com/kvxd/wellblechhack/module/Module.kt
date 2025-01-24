package com.kvxd.wellblechhack.module

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.events.KeyPressEvent
import com.kvxd.wellblechhack.events.ModuleDisableEvent
import com.kvxd.wellblechhack.events.ModuleEnableEvent
import com.kvxd.wellblechhack.setting.Setting
import com.kvxd.wellblechhack.settings.BooleanSetting
import com.kvxd.wellblechhack.settings.KeybindSetting
import com.kvxd.wellblechhack.util.JsonParsable
import com.kvxd.wellblechhack.util.Persistable
import net.minecraft.nbt.NbtCompound
import org.json.JSONObject

abstract class Module(val name: String, val description: String, val category: Category) : Persistable, JsonParsable {

    private val settings = mutableSetOf<Setting<*>>()

    var enabled by boolean("enabled", "Is the module enabled.")
    open val keybind by keybind("keybind", "Toggles the module")

    protected val eventBus = Wellblechhack.EVENT_BUS.forward { enabled }

    // Don't use eventBus because since filters only for enabled
    val keybindHandler = Wellblechhack.EVENT_BUS.handler(KeyPressEvent::class, filter = { it.keyCode == keybind }) {
        if (enabled) disable() else enable()
    }

    fun enable() {
        if (enabled) return // Prevent redundant enabling
        enabled = true
        Wellblechhack.EVENT_BUS.post(ModuleEnableEvent(this))
    }

    fun disable() {
        if (!enabled) return // Prevent redundant disabling
        enabled = false
        Wellblechhack.EVENT_BUS.post(ModuleDisableEvent(this))
    }


    override fun load(tag: NbtCompound) {
        settings.forEach { setting ->
            val settingTag = tag.get(setting.name) as? NbtCompound
            settingTag?.let {
                setting.load(it)
            }
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

    override fun parseJson(jsonObject: JSONObject) {
        jsonObject.put("name", name)
        jsonObject.put("description", description)
        jsonObject.put("category", category.name)

        val settingsJson = JSONObject()
        settings.forEach { setting ->
            val settingJson = JSONObject()
            setting.parseJson(settingJson)
            settingsJson.put(setting.name, settingJson)
        }
        jsonObject.put("settings", settingsJson)
    }

    protected fun boolean(name: String, description: String, display: String = name, default: Boolean = false) =
        BooleanSetting(name, display, default, default)
            .also { settings.add(it) }

    protected fun keybind(name: String, description: String, display: String = name, default: Int = -1) =
        KeybindSetting(name, display, default, default)
            .also { settings.add(it) }
}