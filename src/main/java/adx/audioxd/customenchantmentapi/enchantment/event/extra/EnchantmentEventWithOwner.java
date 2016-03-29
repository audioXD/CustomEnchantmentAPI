package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import org.bukkit.entity.LivingEntity;

import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasOwner;

public abstract class EnchantmentEventWithOwner extends EnchantmentEvent implements hasOwner {
	protected final LivingEntity owner;

	final public LivingEntity getOwner() {
		return owner;
	}

	public EnchantmentEventWithOwner(int lvl, LivingEntity owner) {
		super(lvl);
		this.owner = owner;
	}
}
