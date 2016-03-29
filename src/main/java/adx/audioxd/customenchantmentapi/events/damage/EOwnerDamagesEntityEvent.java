package adx.audioxd.customenchantmentapi.events.damage;


import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Damage;

public class EOwnerDamagesEntityEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable, Damage {
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

	private Entity victum;
	public Entity getVictum() {
		return victum;
	}

	private final DamageCause cause;
	public DamageCause getCause() {
		return cause;
	}

	public EOwnerDamagesEntityEvent(int lvl, ItemStack item, LivingEntity owner, Entity victum, double damage,
			DamageCause cause) {
		super(lvl, owner, item);
		this.victum = victum;
		this.damage = damage;
		this.cause = cause;
	}
}
