package adx.audioxd.customenchantmentapi.listeners.customevents;


import adx.audioxd.customenchantmentapi.enums.ArmorType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public final class ArmorEquipEvent extends PlayerEvent implements Cancellable {

	// Global fields
	private static final HandlerList handlers = new HandlerList();
	// End of Global Fields
	private final EquipMethod equipType;
	private final ArmorType type;
	private final ItemStack oldArmorPiece;
	private boolean cancel = false;
	private ItemStack newArmorPiece;

	// Constructor
	public ArmorEquipEvent(final Player player, final EquipMethod equipType, final ArmorType type,
	                       final ItemStack oldArmorPiece, final ItemStack newArmorPiece) {
		super(player);
		this.equipType = equipType;
		this.type = type;
		this.oldArmorPiece = oldArmorPiece;
		this.newArmorPiece = newArmorPiece;
	}

	// Getters
	public final static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public final HandlerList getHandlers() {
		return handlers;
	}

	public final boolean isCancelled() {
		return cancel;
	}

	public final void setCancelled(final boolean cancel) {
		this.cancel = cancel;
	}

	public final ArmorType getType() {
		return type;
	}

	public final ItemStack getOldArmorPiece() {
		return oldArmorPiece;
	}

	public final ItemStack getNewArmorPiece() {
		return newArmorPiece;
	}

	public final void setNewArmorPiece(final ItemStack newArmorPiece) {
		this.newArmorPiece = newArmorPiece;
	}

	public EquipMethod getMethod() {
		return equipType;
	}

	public enum EquipMethod {
		SHIFT_CLICK, DRAG, HOTBAR, DISPENSER, BROKE, DEATH
	}
}