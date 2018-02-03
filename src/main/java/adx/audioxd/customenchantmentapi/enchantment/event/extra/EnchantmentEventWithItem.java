package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Item;
import org.bukkit.inventory.ItemStack;

public abstract class EnchantmentEventWithItem implements EnchantmentEvent, Item {

  private final ItemStack item;

  // Constructor
  public EnchantmentEventWithItem(ItemStack item) {
    this.item = item;
  }

  // Getters
  final public ItemStack getItem() {
    return item;
  }
}
