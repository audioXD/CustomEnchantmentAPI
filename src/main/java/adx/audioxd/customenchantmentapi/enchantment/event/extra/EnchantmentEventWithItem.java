package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasOwner;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public abstract class EnchantmentEventWithItem extends EnchantmentEvent implements  hasItem {

	private final ItemStack item;

	final public ItemStack getItem() {
		return item;
	}

	public EnchantmentEventWithItem(int lvl,  ItemStack item) {
		super(lvl);
		this.item = item;
	}
}
