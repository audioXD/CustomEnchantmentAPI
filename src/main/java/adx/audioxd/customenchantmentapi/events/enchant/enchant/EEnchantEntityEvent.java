package adx.audioxd.customenchantmentapi.events.enchant.enchant;

import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.GetEntity;
import org.bukkit.entity.Entity;


public class EEnchantEntityEvent extends EEnchantEvent implements GetEntity {

  private final Entity entity;
  @Override
  public Entity getEntity() {
    return entity;
  }

  public EEnchantEntityEvent(Entity entity) {
    this.entity = entity;
  }
}
