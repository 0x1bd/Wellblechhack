package com.kvxd.wellblechhack.mixin;

import com.kvxd.wellblechhack.Wellblechhack;
import com.kvxd.wellblechhack.events.ScreenRenderEvent;
import com.kvxd.wellblechhack.events.GameRenderEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        Wellblechhack.INSTANCE.getEVENT_BUS().post(new GameRenderEvent());
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderWithTooltip(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER))
    private void onRenderScreen(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci, @Local(name = "drawContext") DrawContext drawContext) {
        Wellblechhack.INSTANCE.getEVENT_BUS().post(new ScreenRenderEvent(drawContext));
    }

}
