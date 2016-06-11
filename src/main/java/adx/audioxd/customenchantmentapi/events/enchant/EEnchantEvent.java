package adx.audioxd.customenchantmentapi.events.enchant;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithItem;
import org.bukkit.inventory.ItemStack;

@EnchantmentEventWithLevel
public class EEnchantEvent extends EnchantmentEventWithItem {
	public EEnchantEvent(ItemStack item) {
		super(item);
	}
}
