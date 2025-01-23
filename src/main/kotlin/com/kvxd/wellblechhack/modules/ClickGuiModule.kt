package com.kvxd.wellblechhack.modules

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.events.ModuleEnableEvent
import com.kvxd.wellblechhack.mc
import com.kvxd.wellblechhack.module.Category
import com.kvxd.wellblechhack.module.Module
import com.kvxd.wellblechhack.web.BrowserUtil
import net.minecraft.client.util.InputUtil

object ClickGuiModule : Module("Click-Gui", "Opens the click-gui", Category.THE_BIN) {

    override val keybind: Int by keybind("keybind", "Toggles the module", default = InputUtil.GLFW_KEY_RIGHT_SHIFT)

    init {
        Wellblechhack.EVENT_BUS.handler(ModuleEnableEvent::class) {
            mc.setScreen(BrowserUtil.generate())

            disable()
        }
    }

}