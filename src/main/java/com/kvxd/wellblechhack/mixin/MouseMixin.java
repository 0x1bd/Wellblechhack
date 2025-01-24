package com.kvxd.wellblechhack.mixin;

import com.kvxd.wellblechhack.Wellblechhack;
import com.kvxd.wellblechhack.events.MouseButtonEvent;
import com.kvxd.wellblechhack.events.MousePositionEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {

    @Shadow private double x;

    @Shadow private double y;

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) return;

        MouseButtonEvent p = new MouseButtonEvent(button, action, getX(), getY());
        Wellblechhack.INSTANCE.getEVENT_BUS().post(p);

        if (p.getCancelled())
            ci.cancel();
    }

    @Inject(method = "onCursorPos", at = @At("HEAD"))
    private void onCursorPos(long window, double x, double y, CallbackInfo ci) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) return;

        MousePositionEvent p = new MousePositionEvent(getX(), getY());
        Wellblechhack.INSTANCE.getEVENT_BUS().post(p);
    }

}
