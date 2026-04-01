package dev.starsmc.maceswap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class MaceSwapState {
    private static long lastAction = 0;
    private static int combatStep = 0; 
    public static void handleCombat() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.targetedEntity instanceof LivingEntity target) {
            long now = System.currentTimeMillis();
            if (now - lastAction < 180) return;
            if (target.isBlocking() && combatStep == 0) {
                client.player.getInventory().selectedSlot = 1;
                client.interactionManager.attackEntity(client.player, target);
                combatStep = 1;
                lastAction = now;
            } else if (!target.isBlocking() && combatStep == 0) {
                client.player.getInventory().selectedSlot = 2;
                client.interactionManager.attackEntity(client.player, target);
                combatStep = 2;
                lastAction = now;
            } else if (combatStep == 1) {
                client.player.getInventory().selectedSlot = 2;
                client.interactionManager.attackEntity(client.player, target);
                combatStep = 2;
                lastAction = now;
            } else if (combatStep == 2) {
                client.player.getInventory().selectedSlot = 0;
                combatStep = 0;
                lastAction = now;
            }
        }
    }
}
