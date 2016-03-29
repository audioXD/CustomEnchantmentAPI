package adx.audioxd.customenchantmentapi.events.inventory.hand;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;

public class EItemHandEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private boolean cancelled = false;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	private final HandType handType;
	public HandType getHandType() {
		return this.handType;
	}

	public EItemHandEvent(int lvl, ItemStack item, LivingEntity owner, HandType handType) {
		super(lvl, owner, item);
		this.handType = handType;
	}
}
