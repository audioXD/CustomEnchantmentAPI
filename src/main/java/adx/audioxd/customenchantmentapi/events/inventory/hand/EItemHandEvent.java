package adx.audioxd.customenchantmentapi.events.inventory.hand;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

@EnchantmentEventWithLevel
public class EItemHandEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private final HandType handType;
	public HandType getHandType() { return this.handType; }

	private boolean cancelled = false;
	public boolean isCancelled(){ return cancelled; }
	public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

	public EItemHandEvent(ItemStack item, LivingEntity owner, HandType handType) {
		super(owner, item);
		this.handType = handType;
	}
}
