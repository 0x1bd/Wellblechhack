package com.kvxd.wellblechhack.render.webview

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.mc
import net.ccbluex.liquidbounce.mcef.MCEF
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class WebViewScreen(
    url: String,
    frameRate: Int = 60,
) : Screen(Text.empty()) {

    private val mcefBrowser = MCEF.INSTANCE.createBrowser(
        url,
        true,
        mc.window.framebufferWidth,
        mc.window.framebufferHeight,
        frameRate
    ).apply {
        zoomLevel = 1.0
    }

    private val texture: Identifier = Identifier.of(Wellblechhack.MOD_ID, "webview-screen/${mcefBrowser.hashCode()}")

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

    override fun close() {
        super.close()
        mcefBrowser.close()
        mc.textureManager.destroyTexture(texture)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
        WebViewRenderer.renderTextureFullscreen(context, texture)
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        super.mouseMoved(mouseX, mouseY)
        mcefBrowser.sendMouseMove(mouseX.toInt(), mouseY.toInt())
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        mcefBrowser.sendMousePress(mouseX.toInt(), mouseY.toInt(), button)
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        mcefBrowser.sendMouseRelease(mouseX.toInt(), mouseY.toInt(), button)
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseScrolled(
        mouseX: Double,
        mouseY: Double,
        horizontalAmount: Double,
        verticalAmount: Double,
    ): Boolean {
        mcefBrowser.sendMouseWheel(mouseX.toInt(), mouseY.toInt(), horizontalAmount)
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)
    }

    override fun charTyped(chr: Char, modifiers: Int): Boolean {
        mcefBrowser.sendKeyTyped(chr, modifiers)
        return super.charTyped(chr, modifiers)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        mcefBrowser.sendKeyPress(keyCode, scanCode.toLong(), modifiers)
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        mcefBrowser.sendKeyRelease(keyCode, scanCode.toLong(), modifiers)
        return super.keyReleased(keyCode, scanCode, modifiers)
    }

}