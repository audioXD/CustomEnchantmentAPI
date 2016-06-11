package adx.audioxd.customenchantmentapi.events.damage;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Damage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

@EnchantmentEventWithLevel
public class EOwnerDamagesEntityEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable, Damage {
	private final DamageCause cause;
	public DamageCause getCause() { return cause; }

	private boolean cancelled = false;
	public boolean isCancelled() { return cancelled; }
	public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

	private double damage;
	public double getDamage() { return damage; }
	public void setDamage(double damage) { this.damage = damage; }

	private Entity victim;
	public Entity getVictim() { return victim; }

	// Constructor
	public EOwnerDamagesEntityEvent(ItemStack item, LivingEntity owner, Entity victim, double damage,
	                                DamageCause cause) {
		super(owner, item);
		this.victim = victim;
		this.damage = damage;
		this.cause = cause;
	}
}
