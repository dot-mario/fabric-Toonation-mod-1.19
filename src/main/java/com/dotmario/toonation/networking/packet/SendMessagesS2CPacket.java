package com.dotmario.toonation.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.Objects;

public class SendMessagesS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        String[] data = buf.readString().split("/");
        String translateData = I18n.translate(data[0]);
        if (!Objects.equals(translateData, "0")){
            client.player.sendMessage(Text.of(translateData+data[1]));
        } else {
            client.player.sendMessage(Text.of(data[1]));
        }
    }
}
