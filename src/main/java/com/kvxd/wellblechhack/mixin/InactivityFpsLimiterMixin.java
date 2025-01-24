package com.kvxd.wellblechhack.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.InactivityFpsLimiter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(InactivityFpsLimiter.class)
public class InactivityFpsLimiterMixin {

    @Shadow @Final private MinecraftClient client;

    @ModifyConstant(method = "update", constant = @Constant(intValue = 60))
    private int onUpdate(int original) {
        //if (client.currentScreen instanceof MCEFScreen)
        //    return BrowserUtil.INSTANCE.getFRAMERATE_CAP();
        return original;
    }

}
