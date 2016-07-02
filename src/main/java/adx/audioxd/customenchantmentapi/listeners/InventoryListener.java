package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.events.inventory.EUnequipEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.listeners.extra.EEquip;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static adx.audioxd.customenchantmentapi.EnchantmentRegistry.getEnchantments;

public class InventoryListener implements Listener {
	private Plugin plugin;
	public Plugin getPlugin(){ return plugin; }

	// ---------------------------------------------------------- //
	//                      CONSTRUCTOR                           //
	// ---------------------------------------------------------- //

	public InventoryListener(Plugin plugin) {
		this.plugin = plugin;
	}

	// ---------------------------------------------------------- //
	//                     JOIN/QUIT EVENTS                       //
	// ---------------------------------------------------------- //

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onConnect(PlayerJoinEvent event) {
		EEquip.loadPlayer(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDisconnect(PlayerQuitEvent event) {
		EEquip.clearPlayer(event.getPlayer());
	}

	// ---------------------------------------------------------- //
	//           INVENTORY CHANGE EVENTS(EQUIP,..)                //
	// ---------------------------------------------------------- //

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void inventoryClick(InventoryClickEvent event) {
		new EEquip(plugin.getServer().getPlayer(event.getWhoClicked().getName())).runTaskLater(plugin, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(PlayerItemBreakEvent event) {
		new EEquip(event.getPlayer()).runTaskLater(plugin, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDispense(BlockDispenseEvent e){
		Location loc = e.getBlock().getLocation();
		for(Player p : loc.getWorld().getPlayers()) {
			if(loc.getBlockY() - p.getLocation().getBlockY() >= -1
					&& loc.getBlockY() - p.getLocation().getBlockY() <= 1) {
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
					new EEquip(p).runTaskLater(plugin, 1);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onItemDrop(PlayerDropItemEvent event){
		new EEquip(event.getPlayer()).runTaskLater(plugin, 1);
	}
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onItemPickUp(PlayerPickupItemEvent event){
		new EEquip(event.getPlayer()).runTaskLater(plugin, 1);
	}


	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onHotbarChange(PlayerItemHeldEvent event) {
		new EEquip(event.getPlayer()).runTaskLater(plugin, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if(entity == null) return;

		if(!ItemUtil.isEmpty(ItemUtil.getMainHandItem(entity)))
			CEAPIListenerUtils.itemNotInHand(entity, ItemUtil.getMainHandItem(entity), HandType.MAIN);
		if(!ItemUtil.isEmpty(ItemUtil.getOffHandItem(entity)))
			CEAPIListenerUtils.itemNotInHand(entity, ItemUtil.getOffHandItem(entity), HandType.OFF);

		for(ItemStack item: entity.getEquipment().getArmorContents()){
			CEAPIListenerUtils.unenquipt(entity, item);
		}
	}
}
