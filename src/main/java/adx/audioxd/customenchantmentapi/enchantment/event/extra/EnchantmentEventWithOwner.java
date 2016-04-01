package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.hasOwner;
import org.bukkit.entity.LivingEntity;

public abstract class EnchantmentEventWithOwner extends EnchantmentEvent implements hasOwner {
	protected final LivingEntity owner;

	public EnchantmentEventWithOwner(int lvl, LivingEntity owner) {
		super(lvl);
		this.owner = owner;
	}

	final public LivingEntity getOwner() {
		return owner;
	}
}
