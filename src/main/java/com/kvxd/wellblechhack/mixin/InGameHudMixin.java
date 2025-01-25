package com.kvxd.wellblechhack.mixin;

import com.kvxd.wellblechhack.Wellblechhack;
import com.kvxd.wellblechhack.events.OverlayRenderEvent;
import com.kvxd.wellblechhack.events.RenderEvent;
import com.kvxd.wellblechhack.events.State;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderPre(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        OverlayRenderEvent event = new OverlayRenderEvent(context, State.PRE);
        Wellblechhack.INSTANCE.getEVENT_BUS().post(event);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void onRenderPost(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        OverlayRenderEvent event = new OverlayRenderEvent(context, State.POST);
        Wellblechhack.INSTANCE.getEVENT_BUS().post(event);
    }

}
