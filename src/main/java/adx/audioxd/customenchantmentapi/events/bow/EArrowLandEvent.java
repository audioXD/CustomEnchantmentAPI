package adx.audioxd.customenchantmentapi.events.bow;


import org.bukkit.entity.Arrow;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEvent;

public class EArrowLandEvent extends EnchantmentEvent{
	private Arrow arrow;
	public Arrow getArrow() {
		return arrow;
	}

	public EArrowLandEvent(Arrow arrow, int lvl) {
		super(lvl);
		this.arrow = arrow;
	}
}
