package com.kvxd.wellblechhack.modules

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.events.ModuleEnableEvent
import com.kvxd.wellblechhack.mc
import com.kvxd.wellblechhack.module.Category
import com.kvxd.wellblechhack.module.Module
import com.kvxd.wellblechhack.render.webview.WebView
import com.kvxd.wellblechhack.render.webview.WebViewEnvironment
import com.kvxd.wellblechhack.render.webview.WebViewPosition
import net.minecraft.client.util.InputUtil

object ClickGuiModule : Module("Click-Gui", "Opens the click-gui", Category.THE_BIN) {

    override val keybind: Int by keybind("keybind", "Toggles the module", default = InputUtil.GLFW_KEY_RIGHT_SHIFT)

    init {
        Wellblechhack.EVENT_BUS.handler(ModuleEnableEvent::class) {
            WebViewEnvironment.addView(WebView("http://localhost:5173/", WebViewPosition.FULLSCREEN))

            disable()
        }
    }

}