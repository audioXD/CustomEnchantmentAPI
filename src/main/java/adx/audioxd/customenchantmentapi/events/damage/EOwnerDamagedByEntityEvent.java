package adx.audioxd.customenchantmentapi.events.damage;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

@EnchantmentEventWithLevel
public class EOwnerDamagedByEntityEvent extends EOwnerDamagedEvent {
	private Entity damager;
	public Entity getDamager() { return damager; }

	// Constructor
	public EOwnerDamagedByEntityEvent(ItemStack item, LivingEntity owner, double damage, DamageCause cause,
	                                  Type type, Entity damager) {
		super(item, owner, damage, cause, type);
		this.damager = damager;
	}
}
