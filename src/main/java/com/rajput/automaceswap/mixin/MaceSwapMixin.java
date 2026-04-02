package com.rajput.automaceswap.mixin;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(LocalPlayer.class)
public class MaceSwapMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo info) {
        LocalPlayer p = (LocalPlayer) (Object) this;
        if (p.fallDistance > 0.8f && !p.onGround()) {
            for (int i = 0; i < 9; i++) {
                if (p.getInventory().getItem(i).is(Items.MACE)) {
                    p.getInventory().selectedSlot = i;
                    return;
                }
            }
        }
    }
}
