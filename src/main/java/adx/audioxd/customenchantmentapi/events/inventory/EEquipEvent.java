package adx.audioxd.customenchantmentapi.events.inventory;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.enums.ArmorType;

public class EEquipEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private boolean cancelled = false;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	private final ArmorType armorType;
	public ArmorType getArmorType() {
		return armorType;
	}

	public EEquipEvent(int lvl, ItemStack item, LivingEntity owner) {
		super(lvl, owner, item);
		armorType = ArmorType.matchType(item);
	}
}
