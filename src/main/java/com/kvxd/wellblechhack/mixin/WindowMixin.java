package com.kvxd.wellblechhack.mixin;

import com.kvxd.wellblechhack.Wellblechhack;
import com.kvxd.wellblechhack.events.FramebufferChangeSizeEvent;
import com.kvxd.wellblechhack.events.WindowChangeSizeEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public abstract class WindowMixin {

    @Inject(method = "onWindowSizeChanged", at = @At("HEAD"))
    private void onWindowSizeChanged(long window, int width, int height, CallbackInfo ci) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) return;

        Wellblechhack.INSTANCE.getEVENT_BUS().post(new WindowChangeSizeEvent(width, height));
    }

    @Inject(method = "onFramebufferSizeChanged", at = @At("HEAD"))
    private void onFramebufferSizeChanged(long window, int width, int height, CallbackInfo ci) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) return;

        Wellblechhack.INSTANCE.getEVENT_BUS().post(new FramebufferChangeSizeEvent(width, height));
    }


}
