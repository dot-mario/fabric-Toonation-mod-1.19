package com.dotmario.toonation.Donation;

import com.dotmario.toonation.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final List<EntityType> entityTypesList= List.of(new EntityType[]{EntityType.CREEPER, EntityType.TNT, EntityType.BLAZE, EntityType.CAVE_SPIDER,
            EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_PEARL, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER,
            EntityType.EVOKER_FANGS, EntityType.FIREBALL, EntityType.GHAST, EntityType.GIANT, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HUSK,
            EntityType.ILLUSIONER, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.SHULKER,
            EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.SLIME, EntityType.SPIDER, EntityType.STRAY, EntityType.STRIDER, EntityType.VEX,
            EntityType.VINDICATOR, EntityType.WARDEN, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOGLIN, EntityType.ZOMBIE,
            EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN,
            EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE,
            EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE,
            EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON,
            EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON});
    Random random = new Random();
    public ModDonation(ServerPlayerEntity player, String action) {
        switch (action) {
            case "removeItem" -> RemoveItem(player);
            case "spawnCreeper" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_SPAWNCREEPER));
                SpawnCreeper(player);
            }
            case "spawnTNT" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_SPAWNTNT));
                SpawnTNT(player);
            }
//            case "removeInventory" -> {
//                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_REMOVEINVENTORY));
//                RemoveInventory(player);
//            }
//            case "addInventory" -> {
//                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_ADDINVENTORY));
//                AddInventory(player);
//            }
            case "removeHP" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_REMOVEHP));
                RemoveHP(player);
            }
            case "addHP" -> {
                player.sendMessage(Text.translatable(MESSAGE_TOONATION_ACTION_ADDHP));
                AddHP(player);
            }
            case "spawnRandomMonster" -> {
                SpawnRandomMonster(player);
            }
            default -> LOGGER.info("Action ERROR");
        }
    }

    private void RemoveItem(ServerPlayerEntity player) {
        List<Item> combineItems = new ArrayList();
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (!itemStack.isItemEqualIgnoreDamage(ItemStack.EMPTY)) {
                for (int r=1; r<=itemStack.getCount(); r++) {
                    combineItems.add(itemStack.getItem());
                }
            }
        }
        if (combineItems.size() > 0) {
            ItemStack selectInventoryItem = new ItemStack(combineItems.get(random.nextInt(combineItems.size())),1);
            for(int i = 0; i < player.getInventory().size(); i++){
                ItemStack itm = player.getInventory().getStack(i);
                if(itm != ItemStack.EMPTY && itm.getItem().equals(selectInventoryItem.getItem())) {
                    int amt = itm.getCount() - 1;
                    PacketByteBuf buffer = PacketByteBufs.create();
                    buffer.writeInt(i);
                    ServerPlayNetworking.send(player, ModMessages.INVENTORY_ID, buffer);
                    itm.setCount(amt);
                    player.getInventory().setStack(i, amt > 0 ? itm : ItemStack.EMPTY);
                    break;
                }
            }
            String translationKey = selectInventoryItem.getTranslationKey();
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeString(translationKey + "/ 제거!");
            ServerPlayNetworking.send(player, ModMessages.SendMessages_ID, buffer);
            LOGGER.info(player.getName()+" "+selectInventoryItem.getName()+" remove");
        } else {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeString("0/인벤토리가 비어있습니다!");
            ServerPlayNetworking.send(player, ModMessages.SendMessages_ID, buffer);
            LOGGER.info(player.getName()+" null inventory");
        }
    }
    private void SpawnCreeper(ServerPlayerEntity player) {
        EntityType.CREEPER.spawn((ServerWorld) player.world, null, null, player, player.getBlockPos(),
                SpawnReason.TRIGGERED, true, false);
    }
    private void SpawnTNT(ServerPlayerEntity player) {
        EntityType.TNT.spawn((ServerWorld) player.world, null, null, player, player.getBlockPos(),
                SpawnReason.TRIGGERED, true, false);
    }
//    private void RemoveInventory(ServerPlayerEntity player) {
//        int slot = random.nextInt(player.getInventory().size());
//        player.getInventory().setStack(slot, itemBarrier);
//    }
//    private void AddInventory(ServerPlayerEntity player) {
//        for (int i=0;i<player.getInventory().size();i++) {
//            ItemStack itemStack = player.getInventory().getStack(i);
//            if (itemStack.isItemEqualIgnoreDamage(itemBarrier)) {
//                player.getInventory().setStack(i, ItemStack.EMPTY);
//                break;
//            }
//        }
//    }
    private void RemoveHP(ServerPlayerEntity player) {
        EntityAttributeInstance entityAttributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        double NowHealth = entityAttributeInstance.getBaseValue();
        if (NowHealth>2) {
            double NewHealth = NowHealth - 2;
            entityAttributeInstance.setBaseValue(NewHealth);
            player.setHealth((float) NewHealth);
        } else {
            player.sendMessage(Text.of("HP가 더이상 줄어들지 않습니다!"));
            LOGGER.info("too low HP");
        }
    }
    private void AddHP(ServerPlayerEntity player) {
        EntityAttributeInstance entityAttributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        double NowHealth = entityAttributeInstance.getBaseValue();
        double NewHealth = NowHealth + 2;
        entityAttributeInstance.setBaseValue(NewHealth);
        player.setHealth((float) NewHealth);
    }
    private void SpawnRandomMonster(ServerPlayerEntity player) {
        EntityType entityType = entityTypesList.get(random.nextInt(entityTypesList.size()));
        entityType.spawn((ServerWorld) player.world, null, null, player, player.getBlockPos(),
                SpawnReason.TRIGGERED, true, false);
        String translationKey = entityType.getTranslationKey();
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeString(translationKey+"/ 소환!");
        ServerPlayNetworking.send(player, ModMessages.SendMessages_ID, buffer);
        LOGGER.info(player.getName()+" "+entityType.getName()+" spawn");
    }
}
