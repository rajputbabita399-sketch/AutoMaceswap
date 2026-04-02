package net.wurstclient.hacks;

import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1743;
import net.minecraft.class_1799;
import net.minecraft.class_1835;
import net.minecraft.class_310;
import net.wurstclient.Category;
import net.wurstclient.hack.Hack;

public class TriggerBotHack extends Hack {

    public TriggerBotHack() {
        super("TriggerBot");
    }

    private void performAttack(class_1297 target) {
        class_310 mc = class_310.method_1551();
        if (!(target instanceof class_1309)) return;
        mc.field_1724.method_6104(class_1268.field_5808);
    }
}
