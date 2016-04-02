package adx.audioxd.customenchantmentapi.events.inventory.hand;


import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EItemInHandEvent extends EItemHandEvent {

	// Constructor
	public EItemInHandEvent(int lvl, ItemStack item, LivingEntity owner, HandType handType) {
		super(lvl, item, owner, handType);
	}
}
