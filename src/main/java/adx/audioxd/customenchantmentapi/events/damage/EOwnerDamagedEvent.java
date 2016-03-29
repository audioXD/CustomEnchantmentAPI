package adx.audioxd.customenchantmentapi.events.damage;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Damage;

public class EOwnerDamagedEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable, Damage {
	private boolean cancelled = false;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	private double damage;
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	private final DamageCause cause;
	public DamageCause getCause() {
		return cause;
	}

	private final Type type;
	public Type getType() {
		return type;
	}

	public EOwnerDamagedEvent(int lvl, ItemStack item, LivingEntity owner, double damage, DamageCause cause,
			Type type) {
		super(lvl, owner, item);
		this.damage = damage;
		this.cause = cause;
		this.type = type;
	}

	public static enum Type {
		IN_HAND,
		ARMOR,
		SHIELD,
		SADDLE,
		HORSE_ARMOR;
	}
}
