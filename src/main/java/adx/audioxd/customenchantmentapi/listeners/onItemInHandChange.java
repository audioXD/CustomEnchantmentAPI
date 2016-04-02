package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class onItemInHandChange extends CEPLListener {
	// Constructor
	public onItemInHandChange(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onHotbarChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		if(player == null) return;

		ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
		ItemStack prewItem = player.getInventory().getItem(event.getPreviousSlot());

		if(!ItemUtil.isEmpty(prewItem)) itemNotInHand(player, prewItem, HandType.MAIN);
		if(!ItemUtil.isEmpty(newItem)) itemInHand(player, newItem, HandType.MAIN);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if(entity == null) return;

		if(!ItemUtil.isEmpty(ItemUtil.getMainHandItem(entity)))
			itemNotInHand(entity, ItemUtil.getMainHandItem(entity), HandType.MAIN);
		if(!ItemUtil.isEmpty(ItemUtil.getOffHandItem(entity)))
			itemNotInHand(entity, ItemUtil.getOffHandItem(entity), HandType.OFF);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPickUp(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if(player == null) return;

		ItemStack item = event.getItem().getItemStack();
		if(item == null) return;
		if(ItemUtil.getSlotsList(player, item, SlotType.OUTSIDE).contains(player.getInventory().getHeldItemSlot()))
			itemInHand(player, item, HandType.MAIN);

	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDropUp(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(player == null) return;

		ItemStack item = event.getItemDrop().getItemStack();
		if(item == null) return;

		itemNotInHand(player, item, HandType.MAIN);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void slotChange(InventoryClickEvent event) {
		if(event.getWhoClicked() == null) return;
		if(!(event.getWhoClicked() instanceof Player)) return;
		if(event.getCurrentItem() == null && event.getCursor() == null) return;

		Player player = (Player) event.getWhoClicked();
		if(player == null) return;

		boolean shift = (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT);

		if(shift) {
			ItemStack item = event.getCurrentItem();
			if(ItemUtil.canEnquipt(item, player)
					&& event.getInventory().getName().equalsIgnoreCase("container.crafting"))
				return;
			if(event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
				Map<Integer, ItemStack> items = ItemUtil.getSlots(player, item, event.getSlotType());

				if(event.getSlot() == player.getInventory().getHeldItemSlot() && !items.isEmpty())
					itemNotInHand(player, item, HandType.MAIN);

				if(items.containsKey(player.getInventory().getHeldItemSlot()))
					itemInHand(player, items.get(player.getInventory().getHeldItemSlot()), HandType.MAIN);

			}
		} else {
			ItemStack item = (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR)
					? event.getCurrentItem() : event.getCursor();
			boolean offHand = event.getSlot() == 9 && event.getSlotType().equals(SlotType.QUICKBAR);

			boolean hotbarSwap = (event.getAction().equals(InventoryAction.HOTBAR_SWAP)
					&& (event.getHotbarButton() == player.getInventory().getHeldItemSlot()));

			if(item != null && (event.getSlot() == player.getInventory().getHeldItemSlot() || offHand || hotbarSwap)) {
				if(hotbarSwap && offHand) {
					itemNotInMainHand(player, ItemUtil.getMainHandItem(player));
					itemNotInOffHand(player, ItemUtil.getOffHandItem(player));

					itemInMainHand(player, ItemUtil.getOffHandItem(player));
					itemInOffHand(player, ItemUtil.getMainHandItem(player));
				} else if(offHand && event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
					itemNotInHand(player, event.getCurrentItem(), HandType.OFF);
					itemInHand(player, player.getInventory().getItem(event.getHotbarButton()), HandType.OFF);
				} else {
					itemNotInHand(player, (hotbarSwap ? ItemUtil.getMainHandItem(player) : event.getCurrentItem()),
					              offHand ? HandType.OFF : HandType.MAIN
					);
					itemInHand(player, (hotbarSwap ? event.getCurrentItem() : event.getCursor()),
					           offHand ? HandType.OFF : HandType.MAIN
					);
				}
			}
		}
	}
}
