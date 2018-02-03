package adx.audioxd.customenchantmentapi.events.enchant.unenchant;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Item;
import org.bukkit.inventory.ItemStack;


public class EUnenchantItemEvent extends EUnenchantEvent implements Item {

  private final ItemStack item;
  @Override
  public ItemStack getItem() {
    return item;
  }

  public EUnenchantItemEvent(ItemStack item) {
    this.item = item;
  }
}
