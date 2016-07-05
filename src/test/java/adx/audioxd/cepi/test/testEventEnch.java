package adx.audioxd.cepi.test;


import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventHandler;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EGetItemInHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EGetItemInMainHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EGetItemInOffHandEvent;


public class TestEventEnch extends Enchantment {

	// Constructor
	public TestEventEnch() {
		super("Test Ench", ItemType.ALL_OFF_THE_ABOVE, 10);
	}

	@EnchantmentEventHandler
	public void test1(EGetItemInHandEvent e, int lvl) {
		System.out.println("This should fire! Cancelled: " + e.isCancelled());
	}

	@EnchantmentEventHandler
	public void test2(EGetItemInMainHandEvent e, int lvl) {
		System.out.println("This should fire! Cancelled: " + e.isCancelled());
	}

	@EnchantmentEventHandler
	public void test3(EGetItemInOffHandEvent e, int lvl) {
		System.out.println("This should fire! Cancelled: " + e.isCancelled());
	}
}
