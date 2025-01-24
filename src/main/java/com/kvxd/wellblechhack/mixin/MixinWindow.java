package com.kvxd.wellblechhack.mixin;

import com.kvxd.wellblechhack.Wellblechhack;
import com.kvxd.wellblechhack.events.FrameBufferResizeEvent;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinWindow {

    @Shadow @Final private long handle;

    @Inject(method = "onFramebufferSizeChanged", at = @At("RETURN"))
    public void onFrameBufferSizeChanged(long window, int width, int height, CallbackInfo callbackInfo) {
        if (window == handle) {
            Wellblechhack.INSTANCE.getEVENT_BUS().post(new FrameBufferResizeEvent(width, height));
        }
    }

}
