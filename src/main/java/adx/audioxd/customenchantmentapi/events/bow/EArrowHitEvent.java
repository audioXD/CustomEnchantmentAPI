package adx.audioxd.customenchantmentapi.events.bow;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Damage;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

@EnchantmentEventWithLevel
public class EArrowHitEvent implements EnchantmentEvent, Cancellable, Damage {
	private final LivingEntity target;
	private final Arrow arrow;
	private double damage;
	private boolean cancelled = false;

	// Constructor
	public EArrowHitEvent(LivingEntity target, Arrow arrow, double damage) {
		this.target = target;
		this.arrow = arrow;
		this.damage = damage;
	}

	// Getters
	public LivingEntity getTarget() {
		return target;
	}

	public Arrow getArrow() {
		return arrow;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}