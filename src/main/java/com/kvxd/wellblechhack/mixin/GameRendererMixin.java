package com.kvxd.wellblechhack.mixin;

import com.kvxd.wellblechhack.Wellblechhack;
import com.kvxd.wellblechhack.events.*;
import com.llamalad7.mixinextras.sugar.Local;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderPre(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        RenderEvent event = new RenderEvent(State.PRE);
        Wellblechhack.INSTANCE.getEVENT_BUS().post(event);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V", shift = At.Shift.AFTER))
    private void onRenderPost(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        RenderEvent event = new RenderEvent(State.POST);
        Wellblechhack.INSTANCE.getEVENT_BUS().post(event);
    }

}
