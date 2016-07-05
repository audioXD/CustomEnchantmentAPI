package adx.audioxd.customenchantmentapi.events.enchant.unenchant;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.GetEntity;
import adx.audioxd.customenchantmentapi.events.enchant.enchant.EEnchantEvent;
import org.bukkit.entity.Entity;

public class EUnenchantEntityEvent extends EEnchantEvent implements GetEntity {
	private final Entity entity;
	@Override public Entity getEntity() { return entity; }

	public EUnenchantEntityEvent(Entity entity) {
		this.entity = entity;
	}
}
