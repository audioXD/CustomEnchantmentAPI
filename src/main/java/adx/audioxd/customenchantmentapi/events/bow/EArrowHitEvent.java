package adx.audioxd.customenchantmentapi.events.bow;


import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Damage;

public class EArrowHitEvent extends EnchantmentEvent implements Cancellable, Damage {
	private LivingEntity target;
	public LivingEntity getTarget() {
		return target;
	}

	private Arrow arrow;
	public Arrow getArrow() {
		return arrow;
	}

	private double damage;
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}

	private boolean cancelled = false;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public EArrowHitEvent(LivingEntity target, int lvl, Arrow arrow, double damage) {
		super(lvl);
		this.target = target;
		this.arrow = arrow;
		this.damage = damage;
	}
}