package adx.audioxd.customenchantmentapi.events.inventory.hand;


import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EItemNotInOffHandEvent extends EItemNotInHandEvent {

	// Constructor
	public EItemNotInOffHandEvent(int lvl, ItemStack item, LivingEntity owner) {
		super(lvl, item, owner, HandType.OFF);
	}
}