package adx.audioxd.customenchantmentapi.events.inventory.hand;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class EItemHandEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private final HandType handType;
	private boolean cancelled = false;

	// Constructor
	public EItemHandEvent(int lvl, ItemStack item, LivingEntity owner, HandType handType) {
		super(lvl, owner, item);
		this.handType = handType;
	}

	// Getters
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public HandType getHandType() {
		return this.handType;
	}
}
