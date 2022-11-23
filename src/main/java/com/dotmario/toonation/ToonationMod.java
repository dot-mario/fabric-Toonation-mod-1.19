package com.dotmario.toonation;

import com.dotmario.toonation.config.MidnightConfigExample;
import com.dotmario.toonation.networking.ModMessages;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

// Very important comment
public class ToonationMod implements ModInitializer {

    public static final String MOD_ID = "toonation";
    public static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
//        ModConfigs.registerConfigs();
        MidnightConfig.init(MOD_ID, MidnightConfigExample.class);

        ModMessages.registerC2SPackets();
    }
}
