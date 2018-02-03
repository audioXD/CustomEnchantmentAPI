package adx.audioxd.customenchantmentapi.events.enchant.enchant;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Item;
import org.bukkit.inventory.ItemStack;


public class EEnchantItemEvent extends EEnchantEvent implements Item {

  private final ItemStack item;
  @Override
  public ItemStack getItem() {
    return item;
  }

  public EEnchantItemEvent(ItemStack item) {
    this.item = item;
  }
}
