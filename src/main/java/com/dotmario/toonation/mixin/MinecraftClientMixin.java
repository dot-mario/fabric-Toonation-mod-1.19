package com.dotmario.toonation.mixin;

import com.dotmario.toonation.Donation.DonationCheck;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static com.dotmario.toonation.ToonationMod.LOGGER;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "close",at = @At("HEAD"))
    public void injectClose(CallbackInfo ci) {
        DonationCheck.stopSelenium();
        LOGGER.info("close selenium");
    }
}
