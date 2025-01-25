package com.kvxd.wellblechhack.render.webview

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.eventBus
import com.kvxd.wellblechhack.events.FramebufferChangeSizeEvent
import com.kvxd.wellblechhack.events.MouseButtonEvent
import com.kvxd.wellblechhack.events.MousePositionEvent
import com.kvxd.wellblechhack.mc
import net.ccbluex.liquidbounce.mcef.MCEF
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW

class WebView(
    url: String,
    position: WebViewPosition,
    frameRate: Int = 60,
) {

    private val mcefBrowser = MCEF.INSTANCE.createBrowser(
        url,
        true,
        position.width.coerceAtLeast(1),
        position.height.coerceAtLeast(1),
        frameRate
    ).apply {
        zoomLevel = 1.0
    }

    var position: WebViewPosition = position
        set(value) {
            field = value

            mcefBrowser.resize(
                value.width.coerceAtLeast(1),
                value.height.coerceAtLeast(1)
            )
        }

    val texture: Identifier = Identifier.of(Wellblechhack.MOD_ID, "webview/${mcefBrowser.hashCode()}")

    init {
        mc.textureManager.registerTexture(texture, object : AbstractTexture() {
            override fun getGlId(): Int = mcefBrowser.renderer.textureID
        })
    }

    fun reload(force: Boolean = false) {
        if (force)
            mcefBrowser.reloadIgnoreCache()
        else
            mcefBrowser.reload()
    }

    enum class NavDirection {
        Forward,
        Backward
    }

    fun navigate(direction: NavDirection) {
        if (direction == NavDirection.Forward)
            mcefBrowser.goForward()
        else if (direction == NavDirection.Backward)
            mcefBrowser.goBack()
    }

    fun loadUrl(url: String) {
        mcefBrowser.loadURL(url)
    }

    fun close() {
        mcefBrowser.close()
        WebViewEnvironment.removeView(this)
        mc.textureManager.destroyTexture(texture)

        mousePressHandler.disable()
        mouseReleaseHandler.disable()
        mousePositionHandler.disable()
    }

    fun resize(width: Int, height: Int) {
        if (!position.fullScreen)
            return

        position = position.copy(width = width, height = height)
    }

    private val mousePressHandler = eventBus.handler(MouseButtonEvent::class) {
        if (it.action != GLFW.GLFW_PRESS) return@handler

        mcefBrowser.setFocus(true)
        mcefBrowser.sendMousePress(it.x.toInt(), it.y.toInt(), it.button)
    }

    private val mouseReleaseHandler = eventBus.handler(MouseButtonEvent::class) {
        if (it.action != GLFW.GLFW_RELEASE) return@handler

        mcefBrowser.setFocus(true)
        mcefBrowser.sendMouseRelease(it.x.toInt(), it.y.toInt(), it.button)
    }

    private val mousePositionHandler = eventBus.handler(MousePositionEvent::class) {
        mcefBrowser.sendMouseMove(it.x.toInt(), it.y.toInt())
    }

    private val frameBufferResizeHandler = eventBus.handler(FramebufferChangeSizeEvent::class) {
        resize(it.width, it.height)
    }

}