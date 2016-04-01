package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.enums.ArmorType;
import adx.audioxd.customenchantmentapi.enums.ItemUtilEnum;
import adx.audioxd.customenchantmentapi.listeners.customevents.ArmorEquipEvent;
import adx.audioxd.customenchantmentapi.listeners.customevents.ArmorEquipEvent.EquipMethod;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class onArmorListener extends CEPLListener {

	public onArmorListener(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public final void onInventoryClick(final InventoryClickEvent e) {
		boolean shift = (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT);

		if(e.getSlotType() != SlotType.ARMOR && e.getSlotType() != SlotType.QUICKBAR
				&& !e.getInventory().getName().equalsIgnoreCase("container.crafting"))
			return;

		if(!(e.getWhoClicked() instanceof Player) || e.getCurrentItem() == null) return;

		ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
		if(!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) return;

		if(shift) {
			newArmorType = ArmorType.matchType(e.getCurrentItem());
			if(newArmorType != null) {
				boolean equipping = !(e.getRawSlot() == newArmorType.getSlot());

				if(newArmorType.equals(ArmorType.HELMET)
						&& (equipping ? e.getWhoClicked().getInventory().getHelmet() == null
						: e.getWhoClicked().getInventory().getHelmet() != null)
						|| newArmorType.equals(ArmorType.CHESTPLATE)
						&& (equipping ? e.getWhoClicked().getInventory().getChestplate() == null
						: e.getWhoClicked().getInventory().getChestplate() != null)
						|| newArmorType.equals(ArmorType.ELYTRA)
						&& (equipping ? e.getWhoClicked().getInventory().getChestplate() == null
						: e.getWhoClicked().getInventory().getChestplate() != null)
						|| newArmorType.equals(ArmorType.LEGGINGS)
						&& (equipping ? e.getWhoClicked().getInventory().getLeggings() == null
						: e.getWhoClicked().getInventory().getLeggings() != null)
						|| newArmorType.equals(ArmorType.BOOTS)
						&& (equipping ? e.getWhoClicked().getInventory().getBoots() == null
						: e.getWhoClicked().getInventory().getBoots() != null)) {
					ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(),
					                                                      EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(),
					                                                      equipping ? e.getCurrentItem() : null
					);
					onEvent(armorEquipEvent);
					if(armorEquipEvent.isCancelled()) e.setCancelled(true);

				}
			}
		} else {
			newArmorType = ArmorType
					.matchType(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR
							           ? e.getCurrentItem() : e.getCursor());

			boolean hotbarSwap = false;
			ItemStack hotbar = null;
			if(e.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
				hotbar = e.getWhoClicked().getInventory().getItem(e.getHotbarButton());
				if(!ItemUtil.isEmpty(hotbar) && ItemUtil.canEnquipt(hotbar, e.getWhoClicked())) {
					newArmorType = ArmorType.matchType(hotbar);
					hotbarSwap = true;
				}
			}

			if(newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
				ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), EquipMethod.DRAG,
				                                                      newArmorType, e.getCurrentItem(), hotbarSwap ? hotbar : e.getCursor()
				);
				onEvent(armorEquipEvent);
				if(armorEquipEvent.isCancelled()) {
					e.setCancelled(true);
				}
			}
		}
	}

	public void onEvent(ArmorEquipEvent event) {
		if(!ItemUtil.isEmpty(event.getOldArmorPiece())) unenquipt(event.getPlayer(), event.getOldArmorPiece());
		if(!ItemUtil.isEmpty(event.getNewArmorPiece())) {
			if(event.getMethod().equals(EquipMethod.HOTBAR)
					&& !event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
				itemNotInHand(event.getPlayer(), event.getNewArmorPiece(),
				              ItemUtil.getEquippingHandType(event.getPlayer())
				);
			enquipt(event.getPlayer(), event.getNewArmorPiece());

		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void playerInteractEvent(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Material mat = e.getClickedBlock().getType();
				if(ItemUtilEnum.INTERFACABLE_TILE_ENTITIES.matchType(mat)) return;
			}
			ArmorType newArmorType = ArmorType.matchType(e.getItem());
			if(newArmorType != null) {
				if(newArmorType.equals(ArmorType.HELMET) && e.getPlayer().getInventory().getHelmet() == null
						|| newArmorType.equals(ArmorType.CHESTPLATE)
						&& e.getPlayer().getInventory().getChestplate() == null
						|| newArmorType.equals(ArmorType.ELYTRA) && e.getPlayer().getInventory().getChestplate() == null
						|| newArmorType.equals(ArmorType.LEGGINGS) && e.getPlayer().getInventory().getLeggings() == null
						|| newArmorType.equals(ArmorType.BOOTS) && e.getPlayer().getInventory().getBoots() == null) {
					ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), EquipMethod.HOTBAR,
					                                                      ArmorType.matchType(e.getItem()), null, e.getItem()
					);
					onEvent(armorEquipEvent);
					if(armorEquipEvent.isCancelled()) e.setCancelled(true);

				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void dispenserFireEvent(BlockDispenseEvent e) {
		ArmorType type = ArmorType.matchType(e.getItem());
		if(ArmorType.matchType(e.getItem()) != null) {
			Location loc = e.getBlock().getLocation();
			for(Player p : loc.getWorld().getPlayers()) {
				if(loc.getBlockY() - p.getLocation().getBlockY() >= -1
						&& loc.getBlockY() - p.getLocation().getBlockY() <= 1) {
					if(p.getInventory().getHelmet() == null && type.equals(ArmorType.HELMET)
							|| p.getInventory().getChestplate() == null && type.equals(ArmorType.CHESTPLATE)
							|| p.getInventory().getChestplate() == null && type.equals(ArmorType.ELYTRA)
							|| p.getInventory().getLeggings() == null && type.equals(ArmorType.LEGGINGS)
							|| p.getInventory().getBoots() == null && type.equals(ArmorType.BOOTS)) {
						org.bukkit.block.Dispenser dispenser = (org.bukkit.block.Dispenser) e.getBlock().getState();
						org.bukkit.material.Dispenser dis = (org.bukkit.material.Dispenser) dispenser.getData();
						BlockFace directionFacing = dis.getFacing();
						// Someone told me not to do big if checks because it's
						// hard to read, look at me doing it -_-
						if(directionFacing == BlockFace.EAST && p.getLocation().getBlockX() != loc.getBlockX()
								&& p.getLocation().getX() <= loc.getX() + 2.3 && p.getLocation().getX() >= loc.getX()
								|| directionFacing == BlockFace.WEST && p.getLocation().getX() >= loc.getX() - 1.3
								&& p.getLocation().getX() <= loc.getX()
								|| directionFacing == BlockFace.SOUTH && p.getLocation().getBlockZ() != loc.getBlockZ()
								&& p.getLocation().getZ() <= loc.getZ() + 2.3
								&& p.getLocation().getZ() >= loc.getZ()
								|| directionFacing == BlockFace.NORTH && p.getLocation().getZ() >= loc.getZ() - 1.3
								&& p.getLocation().getZ() <= loc.getZ()) {
							ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, EquipMethod.DISPENSER,
							                                                      ArmorType.matchType(e.getItem()), null, e.getItem()
							);
							onEvent(armorEquipEvent);
							if(armorEquipEvent.isCancelled()) e.setCancelled(true);

							return;
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void itemBreakEvent(PlayerItemBreakEvent e) {
		ArmorType type = ArmorType.matchType(e.getBrokenItem());
		if(type != null) {
			Player p = e.getPlayer();
			ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, EquipMethod.BROKE, type, e.getBrokenItem(), null);
			onEvent(armorEquipEvent);
			if(armorEquipEvent.isCancelled()) {
				ItemStack i = e.getBrokenItem().clone();
				i.setAmount(1);
				i.setDurability((short) (i.getDurability() - 1));
				if(type.equals(ArmorType.HELMET)) {
					p.getInventory().setHelmet(i);
				} else if(type.equals(ArmorType.CHESTPLATE)) {
					p.getInventory().setChestplate(i);
				} else if(type.equals(ArmorType.ELYTRA)) {
					p.getInventory().setChestplate(i);
				} else if(type.equals(ArmorType.LEGGINGS)) {
					p.getInventory().setLeggings(i);
				} else if(type.equals(ArmorType.BOOTS)) {
					p.getInventory().setBoots(i);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerDeathEvent(PlayerDeathEvent e) {
		Player p = e.getEntity();
		for(ItemStack i : p.getInventory().getArmorContents()) {
			if(i != null && !i.getType().equals(Material.AIR)) {
				onEvent(new ArmorEquipEvent(p, EquipMethod.DEATH, ArmorType.matchType(i), i, null));
			}
		}
	}

}
