package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Item;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Owner;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public abstract class EnchantmentEventWithOwnerAndItem implements EnchantmentEvent, Owner, Item {

  protected final LivingEntity owner;
  private final ItemStack item;

  // Constructor
  public EnchantmentEventWithOwnerAndItem(LivingEntity owner, ItemStack item) {
    this.owner = owner;
    this.item = item;
  }

  // Getters
  final public LivingEntity getOwner() {
    return owner;
  }

  final public ItemStack getItem() {
    return item;
  }
}
