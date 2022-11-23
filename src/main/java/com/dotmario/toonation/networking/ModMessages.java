package com.dotmario.toonation.networking;

import com.dotmario.toonation.ToonationMod;
import com.dotmario.toonation.networking.packet.DonationAmountC2PPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.server.ServerPlayNetworkAddon;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier AMOUNT_ID = new Identifier(ToonationMod.MOD_ID,"amount");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(AMOUNT_ID, DonationAmountC2PPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
