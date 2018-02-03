package adx.audioxd.customenchantmentapi.events.inventory.hand;


import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EItemNotInMainHandEvent extends EItemNotInHandEvent {

  public EItemNotInMainHandEvent(ItemStack item, LivingEntity owner) {
    super(item, owner, HandType.MAIN);
  }
}
