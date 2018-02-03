package adx.audioxd.customenchantmentapi.events.inventory;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enums.ArmorType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

@EnchantmentEventWithLevel
public class EUnequipEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {

  private final ArmorType armorType;
  public ArmorType getArmorType() {
    return armorType;
  }

  private boolean cancelled = false;
  public boolean isCancelled() {
    return cancelled;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public EUnequipEvent(ItemStack item, LivingEntity owner) {
    super(owner, item);
    armorType = ArmorType.matchType(item);
  }
}
