package adx.audioxd.customenchantmentapi.events.bow;


import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;

public class EBowShootEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private boolean cancelled = false;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	private final Entity projectile;
	public Entity getProjectile(){
		return projectile;
	}

	public EBowShootEvent(int lvl, ItemStack item, LivingEntity owner, Entity projectile) {
		super(lvl, owner, item);
		this.projectile = projectile;
	}
}
