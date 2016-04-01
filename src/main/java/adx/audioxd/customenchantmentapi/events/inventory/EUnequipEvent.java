package adx.audioxd.customenchantmentapi.events.inventory;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enums.ArmorType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class EUnequipEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private final ArmorType armorType;
	private boolean cancelled = false;

	public EUnequipEvent(int lvl, ItemStack item, LivingEntity owner) {
		super(lvl, owner, item);
		armorType = ArmorType.matchType(item);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public ArmorType getArmorType() {
		return armorType;
	}
}
