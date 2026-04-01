package dev.starsmc.maceswap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.MaceItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class MaceSwapState {
    private static long lastAction = 0;
    private static int combatStep = 0; 

    public static void handleCombat() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.targetedEntity == null) return;

        if (client.targetedEntity instanceof LivingEntity target) {
            long now = System.currentTimeMillis();
            if (now - lastAction < 160) return;

            // Find the slots automatically
            int axeSlot = findItemSlot(client, "axe");
            int maceSlot = findItemSlot(client, "mace");

            if (target.isBlocking() && axeSlot != -1) {
                client.player.getInventory().selectedSlot = axeSlot;
                client.interactionManager.attackEntity(client.player, target);
                lastAction = now;
            } else if (maceSlot != -1) {
                client.player.getInventory().selectedSlot = maceSlot;
                client.interactionManager.attackEntity(client.player, target);
                lastAction = now;
            }
        }
    }

    private static int findItemSlot(MinecraftClient client, String type) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (type.equals("axe") && stack.getItem() instanceof AxeItem) return i;
            if (type.equals("mace") && stack.getItem() instanceof MaceItem) return i;
        }
        return -1; // Not found
    }
}
