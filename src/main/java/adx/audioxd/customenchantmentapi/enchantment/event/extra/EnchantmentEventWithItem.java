package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasItem;
import org.bukkit.inventory.ItemStack;

public abstract class EnchantmentEventWithItem extends EnchantmentEvent implements hasItem {

	private final ItemStack item;

	// Constructor
	public EnchantmentEventWithItem(int lvl, ItemStack item) {
		super(lvl);
		this.item = item;
	}

	// Getters
	final public ItemStack getItem() {
		return item;
	}
}
