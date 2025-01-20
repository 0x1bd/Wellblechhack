package com.kvxd.wellblechhack.mixin;

import com.kvxd.wellblechhack.Wellblechhack;
import com.kvxd.wellblechhack.events.PacketReceivedEvent;
import com.kvxd.wellblechhack.events.PacketSendEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onReceive(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        PacketReceivedEvent p = new PacketReceivedEvent(packet, false);
        Wellblechhack.INSTANCE.getEVENT_BUS().post(p);

        if (p.getCanceled())
            ci.cancel();
    }

    @Inject(method = "sendInternal", at = @At("HEAD"), cancellable = true)
    private void onSendInternal(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        PacketSendEvent p = new PacketSendEvent(packet, false);
        Wellblechhack.INSTANCE.getEVENT_BUS().post(p);

        if (p.getCanceled())
            ci.cancel();
    }

}
