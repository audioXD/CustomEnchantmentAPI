package adx.audioxd.customenchantmentapi.events.inventory.hand;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;

public class EItemInHandEvent extends EItemHandEvent {

	public EItemInHandEvent(int lvl, ItemStack item, LivingEntity owner, HandType handType) {
		super(lvl, item, owner, handType);
	}
}
