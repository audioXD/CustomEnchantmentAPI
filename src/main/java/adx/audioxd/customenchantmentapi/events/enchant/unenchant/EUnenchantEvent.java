package adx.audioxd.customenchantmentapi.events.enchant.unenchant;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventWithLevel;
import org.bukkit.event.Cancellable;

@EnchantmentEventWithLevel
public class EUnenchantEvent implements EnchantmentEvent, Cancellable {
	boolean cancelled = false;
	@Override public boolean isCancelled() { return cancelled; }
	@Override public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}
