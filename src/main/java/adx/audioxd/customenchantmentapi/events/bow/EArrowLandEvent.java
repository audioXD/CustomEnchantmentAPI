package adx.audioxd.customenchantmentapi.events.bow;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEvent;
import org.bukkit.entity.Arrow;

public class EArrowLandEvent extends EnchantmentEvent {
	private final Arrow arrow;

	public EArrowLandEvent(int lvl, Arrow arrow) {
		super(lvl);
		this.arrow = arrow;
	}

	public Arrow getArrow() {
		return arrow;
	}
}
