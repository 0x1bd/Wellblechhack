package com.kvxd.wellblechhack.render.webview

import com.kvxd.eventbus.handler
import com.kvxd.wellblechhack.eventBus
import com.kvxd.wellblechhack.events.OverlayRenderEvent
import com.kvxd.wellblechhack.events.RenderEvent
import com.kvxd.wellblechhack.events.State
import com.kvxd.wellblechhack.mc
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

object WebViewRenderer {

    private val views
        get() = WebViewEnvironment.views

    private val webViewTextureLayer: Function<Identifier, RenderLayer> = Util.memoize { texture: Identifier ->
        RenderLayer.of(
            "webview_textured",
            VertexFormats.POSITION_TEXTURE_COLOR,
            VertexFormat.DrawMode.QUADS,
            786432,
            RenderLayer.MultiPhaseParameters.builder().texture(RenderPhase.Texture(texture, TriState.FALSE, false))
                .program(RenderPhase.POSITION_TEXTURE_COLOR_PROGRAM).transparency(webViewTransparency)
                .depthTest(RenderPhase.LEQUAL_DEPTH_TEST).build(false)
        )
    }

    private val webViewTransparency: RenderPhase.Transparency = RenderPhase.Transparency("webview_transparency", {
        RenderSystem.enableBlend()
        RenderSystem.blendFunc(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA)
    }, {
        RenderSystem.disableBlend()
        RenderSystem.defaultBlendFunc()
    })

    val preRenderHandler = eventBus.handler(RenderEvent::class, filter = { it.state == State.PRE }) {
        WebViewEnvironment.renderWebView()
    }

    val overlayRenderHandler = eventBus.handler(OverlayRenderEvent::class, filter = { it.state == State.POST }) {
        views.forEach { view ->
            val scalar = mc.window.scaleFactor.toFloat()

            val x = view.position.x.toFloat() / scalar
            val y = view.position.y.toFloat() / scalar
            val w = view.position.width.toFloat() / scalar
            val h = view.position.height.toFloat() / scalar

            renderTexture(it.context, view.texture, x, y, w, h)
        }
    }

    fun renderTexture(
        context: DrawContext,
        texture: Identifier,
        x: Float, y: Float,
        width: Float, height: Float,
    ) {
        context.drawTexture(
            webViewTextureLayer,
            texture,
            x.toInt(),
            y.toInt(),
            0f,
            0f,
            width.toInt(),
            height.toInt(),
            width.toInt(),
            height.toInt()
        )
    }

    fun renderTexture(
        context: DrawContext,
        texture: Identifier,
        x: Int, y: Int,
        width: Int, height: Int,
    ) {
        renderTexture(context, texture, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }

    fun renderTextureFullscreen(
        context: DrawContext,
        texture: Identifier
    ) {
        renderTexture(context, texture, 0, 0, mc.window.width, mc.window.height)
    }

}