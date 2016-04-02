package adx.audioxd.customenchantmentapi.events.damage;


import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class EOwnerDamagedByEntityEvent extends EOwnerDamagedEvent {
	private Entity damager;

	// Constructor
	public EOwnerDamagedByEntityEvent(int lvl, ItemStack item, LivingEntity owner, double damage, DamageCause cause,
	                                  Type type, Entity damager) {
		super(lvl, item, owner, damage, cause, type);
		this.damager = damager;
	}

	// Getters
	public Entity getDamager() {
		return damager;
	}

}
