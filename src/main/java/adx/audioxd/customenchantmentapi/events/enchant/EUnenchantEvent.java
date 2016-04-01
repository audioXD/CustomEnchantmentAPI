package adx.audioxd.customenchantmentapi.events.enchant;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasItem;
import org.bukkit.inventory.ItemStack;

/**
 * Created by test on 29/03/2016.
 */
public class EUnenchantEvent implements EnchantmentEvent, hasItem {

	private final ItemStack item;

	public EUnenchantEvent(ItemStack item) {
		this.item = item;
	}

	final public ItemStack getItem() {
		return item;
	}
}
