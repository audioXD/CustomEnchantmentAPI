package adx.audioxd.cepi.test;


import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventHandler;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventPriority;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInHandEvent;

/**
 * Created by test on 26/04/2016.
 */
public class TestEnch extends Enchantment {

	public TestEnch() {
		this("Test Ench", ItemType.ALL_OFF_THE_ABOVE, 10);
	}

	public TestEnch(String name, ItemType type, int maxLvl) {
		super(name, type, maxLvl);
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.MONITOR)
	public void event(EItemInHandEvent e) {
		System.out.println("Fired event with priority MONITOR");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.LOWEST)
	public void event1(EItemInHandEvent e) {
		System.out.println("Fired event with priority LOWEST");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.LOW)
	public void event2(EItemInHandEvent e) {
		System.out.println("Fired event with priority LOW");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.NORMAL)
	public void event3(EItemInHandEvent e) {
		System.out.println("Fired event with priority NORMAL");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.HIGH)
	public void event4(EItemInHandEvent e) {
		System.out.println("Fired event with priority HIGH");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.HIGHEST)
	public void event5(EItemInHandEvent e) {
		System.out.println("Fired event with priority HIGHEST");
	}
}
