package com.kvxd.wellblechhack.web.browser.supports.tab

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.mc
import com.kvxd.wellblechhack.web.browser.supports.JcefBrowser
import net.ccbluex.liquidbounce.mcef.MCEF
import net.ccbluex.liquidbounce.mcef.MCEFBrowser
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.util.Identifier

@Suppress("TooManyFunctions")
class JcefTab(
    private val jcefBrowser: JcefBrowser,
    url: String,
    position: TabPosition,
    frameRate: Int = 60,
    override val takesInput: () -> Boolean
) : ITab, InputAware {

    override var position: TabPosition = position
        set(value) {
            field = value

            mcefBrowser.resize(
                value.width.coerceAtLeast(1),
                value.height.coerceAtLeast(1)
            )
        }

    private val mcefBrowser: MCEFBrowser = MCEF.INSTANCE.createBrowser(
        url,
        true,
        position.width.coerceAtLeast(1),
        position.height.coerceAtLeast(1),
        frameRate
    ).apply {
        // Force zoom level to 1.0 to prevent users from adjusting the zoom level
        // this was possible in earlier versions of MCEF
        zoomLevel = 1.0
    }

    private val texture = Identifier.of(Wellblechhack.MOD_ID, "browser/tab/${mcefBrowser.hashCode()}")

    override var drawn = false
    override var preferOnTop = false

    init {
        mc.textureManager.registerTexture(texture, object : AbstractTexture() {
            override fun getGlId() = mcefBrowser.renderer.textureID
        })
    }

    override fun forceReload() {
        mcefBrowser.reloadIgnoreCache()
    }

    override fun reload() {
        mcefBrowser.reload()
    }

    override fun goForward() {
        mcefBrowser.goForward()
    }

    override fun goBack() {
        mcefBrowser.goBack()
    }

    override fun loadUrl(url: String) {
        mcefBrowser.loadURL(url)
    }

    override fun getUrl() = mcefBrowser.getURL()

    override fun closeTab() {
        mcefBrowser.close()
        jcefBrowser.removeTab(this)
        mc.textureManager.destroyTexture(texture)
    }

    override fun getTexture(): Identifier = texture

    override fun resize(width: Int, height: Int) {
        if (!position.fullScreen) {
            return
        }

        position = position.copy(width = width, height = height)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        mcefBrowser.setFocus(true)
        mcefBrowser.sendMousePress(mouseX.toInt(), mouseY.toInt(), mouseButton)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, mouseButton: Int) {
        mcefBrowser.setFocus(true)
        mcefBrowser.sendMouseRelease(mouseX.toInt(), mouseY.toInt(), mouseButton)
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        mcefBrowser.sendMouseMove(mouseX.toInt(), mouseY.toInt())
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, delta: Double) {
        mcefBrowser.sendMouseWheel(mouseX.toInt(), mouseY.toInt(), delta)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int) {
        mcefBrowser.setFocus(true)
        mcefBrowser.sendKeyPress(keyCode, scanCode.toLong(), modifiers)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int) {
        mcefBrowser.setFocus(true)
        mcefBrowser.sendKeyRelease(keyCode, scanCode.toLong(), modifiers)
    }

    override fun charTyped(codePoint: Char, modifiers: Int) {
        mcefBrowser.setFocus(true)
        mcefBrowser.sendKeyTyped(codePoint, modifiers)
    }

}
