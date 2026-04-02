package net.wurstclient.hacks;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.events.HandleInputListener;
import net.wurstclient.events.PreMotionListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.mixinterface.IKeyMapping;
import net.wurstclient.settings.AttackSpeedSliderSetting;
import net.wurstclient.settings.CheckboxSetting;
import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;
import net.wurstclient.settings.SwingHandSetting;
import net.wurstclient.settings.SwingHandSetting.SwingHand;
import net.wurstclient.settings.filterlists.EntityFilterList;
import net.wurstclient.util.EntityUtils;

public final class TriggerBotHack extends Hack implements PreMotionListener, HandleInputListener {
	private final SliderSetting range = new SliderSetting("Range", 4.5, 1, 6, 0.05, ValueDisplay.DECIMAL);
	private final AttackSpeedSliderSetting speed = new AttackSpeedSliderSetting();
	private final SliderSetting speedRandMS = new SliderSetting("Speed randomization", 100, 0, 1000, 50, ValueDisplay.INTEGER);
	private final SwingHandSetting swingHand = new SwingHandSetting(this, SwingHand.CLIENT);
	private final CheckboxSetting attackWhileBlocking = new CheckboxSetting("Attack while blocking", false);
	private final CheckboxSetting simulateMouseClick = new CheckboxSetting("Simulate mouse click", false);
	private final EntityFilterList entityFilters = EntityFilterList.genericCombat();
	private boolean simulatingMouseClick;
	public TriggerBotHack() { super("TriggerBot"); setCategory(Category.COMBAT); addSetting(range); addSetting(speed); addSetting(speedRandMS); addSetting(swingHand); addSetting(attackWhileBlocking); addSetting(simulateMouseClick); entityFilters.forEach(this::addSetting); }
	@Override protected void onEnable() { speed.resetTimer(speedRandMS.getValue()); EVENTS.add(PreMotionListener.class, this); EVENTS.add(HandleInputListener.class, this); }
	@Override protected void onDisable() { if(simulatingMouseClick) { IKeyMapping.get(MC.options.keyAttack).simulatePress(false); simulatingMouseClick = false; } EVENTS.remove(PreMotionListener.class, this); EVENTS.remove(HandleInputListener.class, this); }
	@Override public void onPreMotion() { if(!simulatingMouseClick) return; IKeyMapping.get(MC.options.keyAttack).simulatePress(false); simulatingMouseClick = false; }
	@Override public void onHandleInput() {
		speed.updateTimer();
		if(!speed.isTimeToAttack() || MC.screen instanceof AbstractContainerScreen) return;
		LocalPlayer player = MC.player;
		if(!attackWhileBlocking.isChecked() && player.isUsingItem()) return;
		if(MC.hitResult == null || !(MC.hitResult instanceof EntityHitResult eResult)) return;
		Entity target = eResult.getEntity();
		if(!isCorrectEntity(target)) return;
		if (player.fallDistance > 0.8f && !player.onGround()) {
			for (int i = 0; i < 9; i++) {
				ItemStack stack = player.getInventory().getStack(i);
				if (stack.isOf(Items.MACE)) { player.getInventory().selectedSlot = i; break; }
			}
		}
		if(simulateMouseClick.isChecked()) { IKeyMapping.get(MC.options.keyAttack).simulatePress(true); simulatingMouseClick = true; }
		else { MC.gameMode.attack(player, target); swingHand.swing(InteractionHand.MAIN_HAND); }
		speed.resetTimer(speedRandMS.getValue());
	}
	private boolean isCorrectEntity(Entity entity) { return EntityUtils.IS_ATTACKABLE.test(entity) && EntityUtils.distanceToHitboxSq(entity) <= range.getValueSq() && entityFilters.testOne(entity); }
}
