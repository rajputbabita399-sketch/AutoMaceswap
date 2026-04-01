package dev.starsmc.maceswap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class MaceSwapState {
    private static long lastAction = 0;
    private static int combatStep = 0; // 0: Idle, 1: Axe used, 2: Mace used
    private static final long DELAY = 160; 

    public static void handleCombat() {
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Only run if looking at an entity
        if (client.targetedEntity instanceof LivingEntity target) {
            long now = System.currentTimeMillis();
            if (now - lastAction < DELAY) return;

            // --- BRANCH 1: TARGET IS BLOCKING (Axe Path) ---
            if (target.isBlocking() && combatStep == 0) {
                client.player.getInventory().selectedSlot = 1; // Swap to Axe (Slot 2)
                client.interactionManager.attackEntity(client.player, target);
                client.player.swingHand(Hand.MAIN_HAND);
                combatStep = 1; // Move to next step (Mace)
                lastAction = now;
            } 
            
            // --- BRANCH 2: TARGET IS OPEN (Direct Mace Path) ---
            else if (!target.isBlocking() && combatStep == 0) {
                client.player.getInventory().selectedSlot = 2; // Swap to Mace (Slot 3)
                client.interactionManager.attackEntity(client.player, target);
                client.player.swingHand(Hand.MAIN_HAND);
                combatStep = 2; // Skip to reset step
                lastAction = now;
            }

            // --- STEP 2: FINISH COMBO WITH MACE ---
            else if (combatStep == 1) {
                client.player.getInventory().selectedSlot = 2; // Swap to Mace (Slot 3)
                client.interactionManager.attackEntity(client.player, target);
                client.player.swingHand(Hand.MAIN_HAND);
                combatStep = 2; 
                lastAction = now;
            }

            // --- STEP 3: RESET TO SWORD ---
            else if (combatStep == 2) {
                client.player.getInventory().selectedSlot = 0; // Back to Sword (Slot 1)
                combatStep = 0; // Ready for next attack
                lastAction = now;
            }
        }
    }
}

