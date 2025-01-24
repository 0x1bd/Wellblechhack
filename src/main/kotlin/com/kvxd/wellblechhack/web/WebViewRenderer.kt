package com.kvxd.wellblechhack.web

import com.kvxd.wellblechhack.Wellblechhack
import com.kvxd.wellblechhack.eventBus
import com.kvxd.wellblechhack.events.FrameBufferResizeEvent
import com.kvxd.wellblechhack.events.GameRenderEvent
import com.kvxd.wellblechhack.mc
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.util.Identifier
import net.minecraft.util.TriState
import net.minecraft.util.Util
import java.util.function.Function

class WebViewRenderer {

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
        WebViewEnvironment.peek()
    }

    @Suppress("unused")
    val windowResizeWHandler = eventBus.handler(FrameBufferResizeEvent::class) { e ->
        WebViewManager.browser!!.resize(e.width, e.height)
    }

    private val texture = Identifier.of(Wellblechhack.MOD_ID, "browser/tab/${WebViewManager.browser.hashCode()}")

    init {
        mc.textureManager.registerTexture(texture, object : AbstractTexture() {
            override fun getGlId() = WebViewManager.browser!!.renderer.textureID
        })
    }

    @Suppress("LongParameterList")
    fun renderTexture(
        context: DrawContext,
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        context.drawTexture(browserTextureLayer, texture, x.toInt(), y.toInt(), 0f, 0f, width.toInt(),
            height.toInt(), width.toInt(), height.toInt())
    }

}