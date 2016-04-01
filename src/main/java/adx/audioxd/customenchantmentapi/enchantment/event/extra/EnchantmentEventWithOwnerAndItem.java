package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasOwner;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public abstract class EnchantmentEventWithOwnerAndItem extends EnchantmentEvent implements hasOwner, hasItem {
	protected final LivingEntity owner;
	private final ItemStack item;

	public EnchantmentEventWithOwnerAndItem(int lvl, LivingEntity owner, ItemStack item) {
		super(lvl);
		this.owner = owner;
		this.item = item;
	}

	final public LivingEntity getOwner() {
		return owner;
	}

	final public ItemStack getItem() {
		return item;
	}
}
