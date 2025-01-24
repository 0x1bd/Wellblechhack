package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.eventBus
import com.kvxd.wellblechhack.events.KeyEvent
import com.kvxd.wellblechhack.events.MouseButtonEvent
import com.kvxd.wellblechhack.events.MousePositionEvent
import com.kvxd.wellblechhack.mc
import org.lwjgl.glfw.GLFW

class WebViewInput(shouldProcess: (Any) -> Boolean) {

    val mouseButtonHandler = eventBus.handler(MouseButtonEvent::class, filter = shouldProcess) {
        if (it.action == GLFW.GLFW_PRESS) {
            WebViewManager.browser!!.sendMousePress(it.x.toInt(), it.y.toInt(), it.button)
        } else if (it.action == GLFW.GLFW_RELEASE) {
            WebViewManager.browser!!.sendMouseRelease(it.x.toInt(), it.y.toInt(), it.button)
        }
    }

    val mousePositionHandler = eventBus.handler(MousePositionEvent::class, filter = shouldProcess) {
        WebViewManager.browser!!.sendMouseMove(
            (calculateWidthFactor() * it.x).toInt(),
            (calculateHeightFactor() * it.y).toInt()
        )
    }

    val keyPressHandler = eventBus.handler(KeyEvent::class, filter = shouldProcess) {
        WebViewManager.browser!!.sendKeyPress(it.keyCode, it.scancode, it.modifiers)
    }

    private fun calculateWidthFactor(): Double =
        (mc.window.framebufferWidth / mc.window.width).toDouble()

    private fun calculateHeightFactor(): Double =
        (mc.window.framebufferHeight / mc.window.height).toDouble()

}