package com.rajput.automaceswap.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MaceSwapMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        // If falling and attacking (or about to), swap to Mace slot
        if (player.fallDistance > 0.5f && !player.isOnGround()) {
            for (int i = 0; i < 9; i++) {
                if (player.getInventory().getStack(i).isOf(Items.MACE)) {
                    if (player.getInventory().selectedSlot != i) {
                        player.getInventory().selectedSlot = i;
                    }
                    break;
                }
            }
        }
    }
}
