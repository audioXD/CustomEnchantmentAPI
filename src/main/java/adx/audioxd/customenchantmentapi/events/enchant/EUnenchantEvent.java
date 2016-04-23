package adx.audioxd.customenchantmentapi.events.enchant;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasItem;
import org.bukkit.inventory.ItemStack;

public class EUnenchantEvent implements EnchantmentEvent, hasItem {

	private final ItemStack item;

	// Constructor
	public EUnenchantEvent(ItemStack item) {
		this.item = item;
	}

	// Getters
	final public ItemStack getItem() {
		return item;
	}
}
