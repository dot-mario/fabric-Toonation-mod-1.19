package com.dotmario.toonation.networking;

import com.dotmario.toonation.ToonationMod;
import com.dotmario.toonation.networking.packet.DonationAmountC2SPacket;
import com.dotmario.toonation.networking.packet.InventoryChangeS2CPacket;
import com.dotmario.toonation.networking.packet.SendMessagesS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier AMOUNT_ID = new Identifier(ToonationMod.MOD_ID,"amount");
    public static final Identifier INVENTORY_ID = new Identifier(ToonationMod.MOD_ID,"inventory");
    public static final Identifier SendMessages_ID = new Identifier(ToonationMod.MOD_ID,"sendmessages");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(AMOUNT_ID, DonationAmountC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(INVENTORY_ID, InventoryChangeS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendMessages_ID, SendMessagesS2CPacket::receive);
    }
}
