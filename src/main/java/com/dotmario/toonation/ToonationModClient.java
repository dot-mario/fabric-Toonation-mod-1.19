package com.dotmario.toonation;

import com.dotmario.toonation.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class ToonationModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
    }
}
