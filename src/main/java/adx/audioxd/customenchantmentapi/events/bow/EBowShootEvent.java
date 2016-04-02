package adx.audioxd.customenchantmentapi.events.bow;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class EBowShootEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private final Entity projectile;
	private boolean cancelled = false;

	// Constructor
	public EBowShootEvent(int lvl, ItemStack item, LivingEntity owner, Entity projectile) {
		super(lvl, owner, item);
		this.projectile = projectile;
	}

	// Getters
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Entity getProjectile() {
		return projectile;
	}
}
