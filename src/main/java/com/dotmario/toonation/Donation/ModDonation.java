package com.dotmario.toonation.Donation;

import com.dotmario.toonation.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.dotmario.toonation.ToonationMod.LOGGER;

public class ModDonation {
    private static final String MESSAGE_TOONATION_ACTION_ADDINVENTORY = "message.toonationmod.addInventory";
    private static final String MESSAGE_TOONATION_ACTION_REMOVEINVENTORY = "message.toonationmod.removeInventory";
    private static final String MESSAGE_TOONATION_ACTION_REMOVEITEM = "message.toonationmod.removeItem";
    private static final String MESSAGE_TOONATION_ACTION_ADDHP = "message.toonationmod.addHP";
    private static final String MESSAGE_TOONATION_ACTION_REMOVEHP = "message.toonationmod.removeHP";
    private static final String MESSAGE_TOONATION_ACTION_SPAWNCREEPER = "message.toonationmod.spawnCreeper";
    private static final String MESSAGE_TOONATION_ACTION_SPAWNTNT = "message.toonationmod.spawnTNT";
    ItemStack itemBarrier = new ItemStack(Items.BARRIER);
    Random random = new Random();
    public ModDonation(ServerPlayerEntity player, String action) {
        switch (action) {
            case "removeItem" -> player.sendMessage(Text.of(RemoveItem(player) + " 제거!"));
            case "spawnCreeper" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_SPAWNCREEPER));
                SpawnCreeper(player);
            }
            case "spawnTNT" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_SPAWNTNT));
                SpawnTNT(player);
            }
            case "removeInventory" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_REMOVEINVENTORY));
                RemoveInventory(player);
            }
            case "addInventory" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_ADDINVENTORY));
                AddInventory(player);
            }
            case "removeHP" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_REMOVEHP));
                RemoveHP(player);
            }
            case "addHP" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_ADDHP));
                AddHP(player);
            }
            default -> LOGGER.info("Action ERROR");
        }
    }

    private Text RemoveItem(ServerPlayerEntity player) {
        List<Item> combineItems = new ArrayList();
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (!itemStack.isItemEqualIgnoreDamage(ItemStack.EMPTY)) {
                for (int r=1; r<=itemStack.getCount(); r++) {
                    combineItems.add(itemStack.getItem());
                }
            }
        }
        ItemStack selectInventoryItem = new ItemStack(combineItems.get(random.nextInt(combineItems.size())),1);
        for(int i = 0; i < player.getInventory().size(); i++){
            ItemStack itm = player.getInventory().getStack(i);
            if(itm != ItemStack.EMPTY && itm.getItem().equals(selectInventoryItem.getItem())) {
                int amt = itm.getCount() - 1;
                itm.setCount(amt);
                player.getInventory().setStack(i, amt > 0 ? itm : ItemStack.EMPTY);
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeInt(i);
                ServerPlayNetworking.send(player, ModMessages.INVENTORY_ID, buffer);
                break;
            }
        }
        return Text.translatable(selectInventoryItem.getTranslationKey());
    }
    private void SpawnCreeper(ServerPlayerEntity player) {
        EntityType.CREEPER.spawn((ServerWorld) player.world, null, null, player, player.getBlockPos(),
                SpawnReason.TRIGGERED, true, false);
    }
    private void SpawnTNT(ServerPlayerEntity player) {
        EntityType.TNT.spawn((ServerWorld) player.world, null, null, player, player.getBlockPos(),
                SpawnReason.TRIGGERED, true, false);
    }
    private void RemoveInventory(ServerPlayerEntity player) {
        int slot = random.nextInt(player.getInventory().size());
        player.getInventory().setStack(slot, itemBarrier);
    }
    private void AddInventory(ServerPlayerEntity player) {
        for (int i=0;i<player.getInventory().size();i++) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (itemStack.isItemEqualIgnoreDamage(itemBarrier)) {
                player.getInventory().setStack(i, ItemStack.EMPTY);
                break;
            }
        }
    }
    private void RemoveHP(ServerPlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).
                addPersistentModifier(new EntityAttributeModifier("thisnamedosentmatter", -2,
                        EntityAttributeModifier.Operation.ADDITION));
    }
    private void AddHP(ServerPlayerEntity player) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).
                addPersistentModifier(new EntityAttributeModifier("thisnamedosentmatter", 2,
                        EntityAttributeModifier.Operation.ADDITION));
    }
}
