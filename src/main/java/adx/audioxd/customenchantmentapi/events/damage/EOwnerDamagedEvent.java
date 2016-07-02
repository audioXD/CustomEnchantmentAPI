package adx.audioxd.customenchantmentapi.events.damage;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Damage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

@EnchantmentEventWithLevel
public class EOwnerDamagedEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable, Damage {
	private final DamageCause cause;
	public DamageCause getCause() { return cause; }

	private final Type type;
	public Type getType() { return type; }

	private boolean cancelled = false;
	public boolean isCancelled() { return cancelled; }
	public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

	private double damage;
	public double getDamage() { return damage; }
	public void setDamage(double damage) { this.damage = damage; }

	// Constructor
	public EOwnerDamagedEvent(ItemStack item, LivingEntity owner, double damage, DamageCause cause,
	                          Type type) {
		super(owner, item);
		this.damage = damage;
		this.cause = cause;
		this.type = type;
	}

	public enum Type {
		IN_HAND,
		ARMOR,
		SHIELD,
		HORSE_ARMOR
	}
}
