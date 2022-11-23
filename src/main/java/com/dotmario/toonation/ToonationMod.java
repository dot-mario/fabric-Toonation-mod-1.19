package com.dotmario.toonation;

import com.dotmario.toonation.networking.ModMessages;
import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class ToonationMod implements ModInitializer {

    public static final String MOD_ID = "toonation";
    public static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        ModMessages.registerC2SPackets();
    }
}
