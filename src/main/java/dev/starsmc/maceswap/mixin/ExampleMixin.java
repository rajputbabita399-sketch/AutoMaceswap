package dev.starsmc.maceswap.mixin;

import dev.starsmc.maceswap.MaceSwapState;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ExampleMixin {
    @Inject(at = @At("TAIL"), method = "render")
    private void onRender(DrawContext context, float tickDelta, CallbackInfo info) {
        // Only runs when the UI is visible (Safe from Black Screen)
        MaceSwapState.handleCombat();
    }
}
