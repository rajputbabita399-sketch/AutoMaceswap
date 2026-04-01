package dev.starsmc.maceswap.mixin;

import dev.starsmc.maceswap.MaceSwapState;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ExampleMixin {
    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        MaceSwapState.handleCombat();
    }
}
