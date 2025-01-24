package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.mc
import com.kvxd.wellblechhack.web.browser.BrowserManager
import com.kvxd.wellblechhack.web.browser.supports.tab.ITab
import com.kvxd.wellblechhack.web.browser.supports.tab.TabPosition
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class BrowserScreen(val url: String, title: Text = Text.literal("")) : Screen(title) {

    lateinit var browserTab: ITab

    override fun init() {
        val position = TabPosition(
            20,
            20,
            ((width - 20) * mc.window.scaleFactor).toInt(),
            ((height - 50) * mc.window.scaleFactor).toInt()
        )

        println("aaaa")
        val browser = BrowserManager.browser ?: return
        println("bobb")

        browserTab = browser.createInputAwareTab(url, position, 144) { mc.currentScreen == this }
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val scaleFactor = mc.window.scaleFactor.toFloat()
        val x = browserTab!!.position.x.toFloat() / scaleFactor
        val y = browserTab!!.position.y.toFloat() / scaleFactor
        val w = browserTab!!.position.width.toFloat() / scaleFactor
        val h = browserTab!!.position.height.toFloat() / scaleFactor

        println("Aaaaaa")

        BrowserManager.browserDrawer.renderTexture(context, browserTab!!.getTexture(), x, y, w, h)
        browserTab!!.drawn = true

        // render nothing
    }

    override fun shouldPause() = false

    override fun close() {
        // Close all tabs
        browserTab?.closeTab()

        super.close()
    }

    override fun shouldCloseOnEsc() = true

}
