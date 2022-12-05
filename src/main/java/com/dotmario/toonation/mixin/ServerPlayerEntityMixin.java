package com.dotmario.toonation.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "dropSelectedItem", at= @At("HEAD"), cancellable = true)
    public void injectDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if(Objects.equals(player.getInventory().dropSelectedItem(entireStack).getItem(), Items.BARRIER)) {
            cir.cancel();
        }
    }

    @Inject(method = "dropItem", at= @At("HEAD"), cancellable = true)
    public void injectDropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if(stack.getItem()==Items.BARRIER) {
            cir.cancel();
        }
    }
}
