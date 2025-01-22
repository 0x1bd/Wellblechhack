package com.kvxd.wellblechhack.mcef

import com.cinemamod.mcef.MCEF
import com.cinemamod.mcef.MCEFBrowser
import com.kvxd.wellblechhack.mc
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.ShaderProgramKeys
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.*
import net.minecraft.text.Text

class ExampleScreen(text: Text) : Screen(text) {

    private var browser: MCEFBrowser? = null

    override fun init() {
        super.init()
        if (browser == null) {
            val url = "https://www.google.com"
            val transparent = true
            browser = MCEF.createBrowser(url, transparent)
            resizeBrowser()
        }
    }

    private fun mouseX(x: Double): Int {
        return ((x - BROWSER_DRAW_OFFSET) * mc.window.scaleFactor).toInt()
    }

    private fun mouseY(y: Double): Int {
        return ((y - BROWSER_DRAW_OFFSET) * mc.window.scaleFactor).toInt()
    }

    private fun scaleX(x: Int): Int {
        return ((x - BROWSER_DRAW_OFFSET * 2) * mc.window.scaleFactor).toInt()
    }

    private fun scaleY(y: Int): Int {
        return ((y - BROWSER_DRAW_OFFSET * 2) * mc.window.scaleFactor).toInt()
    }

    private fun resizeBrowser() {
        if (width > 100 && height > 100) {
            browser!!.resize(scaleX(width), scaleY(height))
        }
    }

    override fun resize(minecraft: MinecraftClient?, i: Int, j: Int) {
        super.resize(minecraft, i, j)
        resizeBrowser()
    }

    override fun close() {
        browser!!.close()
        super.close()
    }

    override fun render(context: DrawContext, i: Int, j: Int, f: Float) {
        super.render(context, i, j, f)
        RenderSystem.disableDepthTest()
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR)
        RenderSystem.setShaderTexture(0, browser!!.renderer.textureID)
        val t = Tessellator.getInstance()
        val buffer: BufferBuilder = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)
        buffer.vertex(BROWSER_DRAW_OFFSET.toFloat(), (height - BROWSER_DRAW_OFFSET).toFloat(), 0F)
            .texture(0f, 1f)
            .color(255, 255, 255, 255)
        buffer.vertex((width - BROWSER_DRAW_OFFSET).toFloat(), (height - BROWSER_DRAW_OFFSET).toFloat(), 0F)
            .texture(1f, 1f)
            .color(255, 255, 255, 255)
        buffer.vertex((width - BROWSER_DRAW_OFFSET).toFloat(), BROWSER_DRAW_OFFSET.toFloat(), 0F)
            .texture(1f, 0f)
            .color(255, 255, 255, 255)
        buffer.vertex(BROWSER_DRAW_OFFSET.toFloat(), BROWSER_DRAW_OFFSET.toFloat(), 0F)
            .texture(0f, 0f)
            .color(255, 255, 255, 255)
        BufferRenderer.drawWithGlobalProgram(buffer.end())
        RenderSystem.setShaderTexture(0, 0)
        RenderSystem.enableDepthTest()
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        browser!!.sendMousePress(mouseX(mouseX), mouseY(mouseY), button)
        browser!!.setFocus(true)
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        browser!!.sendMouseRelease(mouseX(mouseX), mouseY(mouseY), button)
        browser!!.setFocus(true)
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        browser!!.sendMouseMove(mouseX(mouseX), mouseY(mouseY))
        super.mouseMoved(mouseX, mouseY)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double): Boolean {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        browser!!.sendMouseWheel(mouseX(mouseX), mouseY(mouseY), scrollY, 0)
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        browser!!.sendKeyPress(keyCode, scanCode.toLong(), modifiers)
        browser!!.setFocus(true)
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        browser!!.sendKeyRelease(keyCode, scanCode.toLong(), modifiers)
        browser!!.setFocus(true)
        return super.keyReleased(keyCode, scanCode, modifiers)
    }

    override fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        if (codePoint == 0.toChar()) return false
        browser!!.sendKeyTyped(codePoint, modifiers)
        browser!!.setFocus(true)
        return super.charTyped(codePoint, modifiers)
    }

    companion object {
        private const val BROWSER_DRAW_OFFSET = 20
    }
}