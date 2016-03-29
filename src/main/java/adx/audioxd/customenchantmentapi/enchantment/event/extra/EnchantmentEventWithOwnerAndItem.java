package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasOwner;

public abstract class EnchantmentEventWithOwnerAndItem extends EnchantmentEvent implements hasOwner, hasItem {
	protected final LivingEntity owner;

	final public LivingEntity getOwner() {
		return owner;
	}

	private final ItemStack item;

	final public ItemStack getItem() {
		return item;
	}

	public EnchantmentEventWithOwnerAndItem(int lvl, LivingEntity owner, ItemStack item) {
		super(lvl);
		this.owner = owner;
		this.item = item;
	}
}
