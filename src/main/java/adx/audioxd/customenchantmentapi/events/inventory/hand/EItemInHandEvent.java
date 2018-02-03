package adx.audioxd.customenchantmentapi.events.inventory.hand;


import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EItemInHandEvent extends EItemHandEvent {

  public EItemInHandEvent(ItemStack item, LivingEntity owner, HandType handType) {
    super(item, owner, handType);
  }
}
