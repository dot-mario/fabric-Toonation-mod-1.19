package com.dotmario.toonation;

import com.dotmario.toonation.Donation.DonationCheck;
import com.dotmario.toonation.config.MidnightConfig;
import com.dotmario.toonation.config.MidnightConfigExample;
import com.dotmario.toonation.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

import static com.dotmario.toonation.ToonationMod.MOD_ID;

public class ToonationModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MidnightConfig.init(MOD_ID, MidnightConfigExample.class);
        ModMessages.registerS2CPackets();
        DonationCheck donationCheck = new DonationCheck();
        donationCheck.start();
    }
}
