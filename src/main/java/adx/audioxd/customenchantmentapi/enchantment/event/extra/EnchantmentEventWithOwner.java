package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Owner;
import org.bukkit.entity.LivingEntity;

public abstract class EnchantmentEventWithOwner implements EnchantmentEvent, Owner {

  protected final LivingEntity owner;

  // Constructor
  public EnchantmentEventWithOwner(LivingEntity owner) {
    this.owner = owner;
  }

  // Getters
  final public LivingEntity getOwner() {
    return owner;
  }
}
