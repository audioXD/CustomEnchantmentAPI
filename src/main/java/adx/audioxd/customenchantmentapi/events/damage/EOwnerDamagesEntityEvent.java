package adx.audioxd.customenchantmentapi.events.damage;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class EOwnerDamagesEntityEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable, Damage {
	private final DamageCause cause;
	private boolean cancelled = false;
	private double damage;
	private Entity victum;

	public EOwnerDamagesEntityEvent(int lvl, ItemStack item, LivingEntity owner, Entity victum, double damage,
	                                DamageCause cause) {
		super(lvl, owner, item);
		this.victum = victum;
		this.damage = damage;
		this.cause = cause;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public Entity getVictum() {
		return victum;
	}

	public DamageCause getCause() {
		return cause;
	}
}
