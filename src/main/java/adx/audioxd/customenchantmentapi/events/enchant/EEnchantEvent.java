package adx.audioxd.customenchantmentapi.events.enchant;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithItem;
import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * Created by test on 29/03/2016.
 */
public class EEnchantEvent extends EnchantmentEventWithItem {

	public EEnchantEvent(int lvl, ItemStack item) {
		super(lvl, item);
	}
}
