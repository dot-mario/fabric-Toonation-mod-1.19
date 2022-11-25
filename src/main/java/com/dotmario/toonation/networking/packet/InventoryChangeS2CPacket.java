package com.dotmario.toonation.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import static com.dotmario.toonation.ToonationMod.LOGGER;

public class InventoryChangeS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Everything here happens ONLY on the Client!
        int slot = buf.readInt();
        ItemStack itemStack = client.player.getInventory().getStack(slot);;
        int amt = itemStack.getCount() - 1;
        itemStack.setCount(amt);
        client.player.getInventory().setStack(slot, amt > 0 ? itemStack : ItemStack.EMPTY);
        LOGGER.info(client.player.getEntityName()+" slot:"+slot+" item:"+ Text.translatable(itemStack.getTranslationKey()));
    }
}
