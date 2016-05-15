package adx.audioxd.cepi.test;


import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventHandler;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInMainHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInOffHandEvent;


public class TestEventEnch extends Enchantment {

	// Constructor
	public TestEventEnch() {
		super("Test Ench", ItemType.ALL_OFF_THE_ABOVE, 10);
	}

	@EnchantmentEventHandler
	public void test1(EItemInHandEvent e, int lvl) {
		System.out.println("This should fire! Cancelled: " + e.isCancelled());
	}

	@EnchantmentEventHandler
	public void test2(EItemInMainHandEvent e, int lvl) {
		System.out.println("This should fire! Cancelled: " + e.isCancelled());
	}

	@EnchantmentEventHandler
	public void test3(EItemInOffHandEvent e, int lvl) {
		System.out.println("This should fire! Cancelled: " + e.isCancelled());
	}
}
