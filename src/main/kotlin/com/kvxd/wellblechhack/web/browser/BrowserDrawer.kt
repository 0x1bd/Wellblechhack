package com.kvxd.wellblechhack.web.browser

import com.kvxd.wellblechhack.eventBus
import com.kvxd.wellblechhack.events.FrameBufferResizeEvent
import com.kvxd.wellblechhack.events.GameRenderEvent
import com.kvxd.wellblechhack.events.ScreenRenderEvent
import com.kvxd.wellblechhack.mc
import com.kvxd.wellblechhack.web.browser.supports.IBrowser
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.util.Identifier
import net.minecraft.util.TriState
import net.minecraft.util.Util
import java.util.function.Function

class BrowserDrawer(val browser: () -> IBrowser?) {

    private val tabs
        get() = browser()?.getTabs() ?: emptyList()


    private val browserTextureLayer: Function<Identifier, RenderLayer> = Util.memoize { texture: Identifier ->
        RenderLayer.of(
            "browser_textured",
            VertexFormats.POSITION_TEXTURE_COLOR,
            VertexFormat.DrawMode.QUADS,
            786432,
            RenderLayer.MultiPhaseParameters.builder()
                .texture(RenderPhase.Texture(texture, TriState.FALSE, false))
                .program(RenderPhase.POSITION_TEXTURE_COLOR_PROGRAM)
                .transparency(browserTransparency)
                .depthTest(RenderPhase.LEQUAL_DEPTH_TEST)
                .build(false)
        )
    }

    private val browserTransparency: RenderPhase.Transparency = RenderPhase.Transparency("browser_transparency", {
        RenderSystem.enableBlend()
        RenderSystem.blendFunc(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA)
    }, {
        RenderSystem.disableBlend()
        RenderSystem.defaultBlendFunc()
    })

    @Suppress("unused")
    val preRenderHandler = eventBus.handler(GameRenderEvent::class) {
        browser()?.drawGlobally()

        for (tab in tabs) {
            tab.drawn = false
        }
    }

    @Suppress("unused")
    val windowResizeWHandler = eventBus.handler(FrameBufferResizeEvent::class) { ev ->
        for (tab in tabs) {
            tab.resize(ev.width, ev.height)
        }
    }

    @Suppress("unused")
    val onScreenRender = eventBus.handler(ScreenRenderEvent::class) {
        for (tab in tabs) {
            if (tab.drawn) {
                continue
            }

            val scaleFactor = mc.window.scaleFactor.toFloat()
            val x = tab.position.x.toFloat() / scaleFactor
            val y = tab.position.y.toFloat() / scaleFactor
            val w = tab.position.width.toFloat() / scaleFactor
            val h = tab.position.height.toFloat() / scaleFactor

            println("Aaaaaa")

            renderTexture(it.context, tab.getTexture(), x, y, w, h)
            tab.drawn = true
        }
    }

    /*
    private var shouldReload = false

    @Suppress("unused")
    val onReload = handler<ResourceReloadEvent> {
        shouldReload = true
    }
     */

    @Suppress("LongParameterList")
    fun renderTexture(
        context: DrawContext,
        texture: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        context.drawTexture(browserTextureLayer, texture, x.toInt(), y.toInt(), 0f, 0f, width.toInt(),
            height.toInt(), width.toInt(), height.toInt())
    }

}
