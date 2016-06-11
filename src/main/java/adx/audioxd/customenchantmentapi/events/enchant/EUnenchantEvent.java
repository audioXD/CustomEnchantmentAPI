package adx.audioxd.customenchantmentapi.events.enchant;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Item;
import org.bukkit.inventory.ItemStack;


public class EUnenchantEvent implements EnchantmentEvent, Item {
	private final ItemStack item;
	final public ItemStack getItem() { return item; }

	public EUnenchantEvent(ItemStack item) {
		this.item = item;
	}
}
