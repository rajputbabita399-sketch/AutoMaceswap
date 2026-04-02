package net.wurstclient.hacks;

import net.minecraft.net.minecraft.util.Hand;
import net.minecraft.net.minecraft.entity.Entity;
import net.minecraft.net.minecraft.entity.LivingEntity;
import net.minecraft.net.minecraft.item.MaceItem;
import net.minecraft.net.minecraft.item.ItemStack;
import net.minecraft.net.minecraft.item.SwordItem;
import net.minecraft.net.minecraft.client.MinecraftClient;
import net.wurstclient.Category;
import net.wurstclient.Hack;

public class TriggerBotHack extends Hack {

    public TriggerBotHack() {
        super("TriggerBot");
    }

    private void performAttack(net.minecraft.entity.Entity target) {
        net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.method_1551();
        if (!(target instanceof net.minecraft.entity.LivingEntity)) return;
        mc.field_1724.method_6104(net.minecraft.util.Hand.field_5808);
    }
}
