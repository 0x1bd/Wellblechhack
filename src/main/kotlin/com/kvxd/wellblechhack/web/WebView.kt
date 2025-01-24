package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.mc
import com.kvxd.wellblechhack.web.WebViewManager.browser
import net.ccbluex.liquidbounce.mcef.MCEF
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class WebView(private val url: String, private val dimension: WebViewDimension) : Screen(Text.empty()) {

    override fun init() {
        super.init()

        // Create browser with framebuffer dimensions
        browser = MCEF.INSTANCE.createBrowser(
            url,
            true,
            mc.window.framebufferWidth,
            mc.window.framebufferHeight,
            144
        )

        browser!!.loadURL(url)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        val scaleFactor = mc.window.scaleFactor.toFloat()
        // Convert dimensions to GUI scale
        val x = dimension.x.toFloat() / scaleFactor
        val y = dimension.y.toFloat() / scaleFactor
        val w = dimension.width.toFloat() / scaleFactor
        val h = dimension.height.toFloat() / scaleFactor

        WebViewManager.renderer.renderTexture(context, x, y, w, h)
    }

    override fun close() {
        super.close()
        browser!!.close()
    }
}