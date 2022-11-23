package com.dotmario.toonation;

import com.dotmario.toonation.Donation.DonationCheck;
import com.dotmario.toonation.config.MidnightConfig;
import com.dotmario.toonation.config.MidnightConfigExample;
import com.dotmario.toonation.networking.ModMessages;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Very important comment
public class ToonationMod implements ModInitializer {

    public static final String MOD_ID = "toonation";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        MidnightConfig.init(MOD_ID, MidnightConfigExample.class);
        DonationCheck donationCheck = new DonationCheck(true);
        donationCheck.start();
        ModMessages.registerC2SPackets();
    }
}
