package adx.audioxd.customenchantmentapi.events.inventory.hand;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;

public class EItemInMainHandEvent extends EItemInHandEvent {

	public EItemInMainHandEvent(int lvl, ItemStack item, LivingEntity owner) {
		super(lvl, item, owner, HandType.MAIN);
	}
}
