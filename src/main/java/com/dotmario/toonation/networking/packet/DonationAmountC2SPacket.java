package com.dotmario.toonation.networking.packet;

import com.dotmario.toonation.Donation.ModDonation;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static com.dotmario.toonation.ToonationMod.LOGGER;

public class DonationAmountC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Everything here happens ONLY on the Server!
//        EntityType.COW.spawn((ServerWorld) player.world, null, null, player, player.getBlockPos(),
//                SpawnReason.TRIGGERED, true, false);
        String action = buf.readString();
        LOGGER.info(player.getEntityName()+" "+action);
        new ModDonation(player, action);
    }
}
