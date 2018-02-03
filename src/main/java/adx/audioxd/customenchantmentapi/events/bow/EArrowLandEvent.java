package adx.audioxd.customenchantmentapi.events.bow;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import org.bukkit.entity.Arrow;

@EnchantmentEventWithLevel
public class EArrowLandEvent implements EnchantmentEvent {

  private final Arrow arrow;
  public Arrow getArrow() {
    return arrow;
  }

  // Constructor
  public EArrowLandEvent(Arrow arrow) {
    this.arrow = arrow;
  }
}
