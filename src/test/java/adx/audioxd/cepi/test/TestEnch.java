package adx.audioxd.cepi.test;


import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventHandler;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventPriority;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.bow.EBowShootEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EGetItemInHandEvent;


public class TestEnch extends Enchantment {

	// Constructor
	public TestEnch() {
		super("Test Ench", ItemType.ALL_OFF_THE_ABOVE, 10);
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.MONITOR)
	public void event(EGetItemInHandEvent e) {
		System.out.println("Fired event with priority MONITOR");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.LOWEST)
	public void event1(EGetItemInHandEvent e) {
		System.out.println("Fired event with priority LOWEST");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.LOW)
	public void event2(EGetItemInHandEvent e) {
		System.out.println("Fired event with priority LOW");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.NORMAL)
	public void event3(EGetItemInHandEvent e) {
		System.out.println("Fired event with priority NORMAL");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.HIGH)
	public void event4(EGetItemInHandEvent e) {
		System.out.println("Fired event with priority HIGH");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.HIGHEST)
	public void event5(EGetItemInHandEvent e) {
		System.out.println("Fired event with priority HIGHEST");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.HIGHEST)
	public void testCancelled(EBowShootEvent e) {
		e.setCancelled(true);
		System.out.println("Cancelled event " + EBowShootEvent.class.getName());
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.HIGH, ignoreCancelled = true)
	public void testCancelled1(EBowShootEvent e) {
		e.setCancelled(true);
		System.out.println("Error this shouldn't fire!");
	}

	@EnchantmentEventHandler(priority = EnchantmentEventPriority.HIGH)
	public void testCancelled2(EBowShootEvent e) {
		e.setCancelled(true);
		System.out.println("This should fire! Cancelled: " + e.isCancelled());
	}
}
