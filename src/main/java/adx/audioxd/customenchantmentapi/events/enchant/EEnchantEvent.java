package adx.audioxd.customenchantmentapi.events.enchant;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithItem;
import org.bukkit.inventory.ItemStack;

public class EEnchantEvent extends EnchantmentEventWithItem {

	// Constructor
	public EEnchantEvent(int lvl, ItemStack item) {
		super(lvl, item);
	}
}
