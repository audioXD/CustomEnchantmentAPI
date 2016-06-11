package adx.audioxd.customenchantmentapi.events.inventory.hand;


import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EItemNotInHandEvent extends EItemHandEvent {
	public EItemNotInHandEvent(ItemStack item, LivingEntity owner, HandType handType) {
		super(item, owner, handType);
	}
}
