package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.bow.EArrowHitEvent;
import adx.audioxd.customenchantmentapi.events.bow.EArrowLandEvent;
import adx.audioxd.customenchantmentapi.events.bow.EBowShootEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedByEntityEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagesEntityEvent;
import adx.audioxd.customenchantmentapi.events.inventory.EUnequipEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.events.world.EBlockBreakEvent;
import adx.audioxd.customenchantmentapi.events.world.EBlockDamageEvent;
import adx.audioxd.customenchantmentapi.events.world.EBlockPlaceEvent;
import adx.audioxd.customenchantmentapi.events.world.EInteractEvent;
import adx.audioxd.customenchantmentapi.listeners.extra.EEquip;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static adx.audioxd.customenchantmentapi.EnchantmentRegistry.getEnchantments;
import static adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent.Type;

public class DefaultEventsListener extends CEAPIListenerUtils {

	// ---------------------------------------------------------- //
	//                      CONSTRUCTOR                           //
	// ---------------------------------------------------------- //

	public DefaultEventsListener(CustomEnchantmentAPI plugin) {
		super(plugin);
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
	//                      DAMAGE EVENTS                         //
	// ---------------------------------------------------------- //

	public static boolean active = true;

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPureDamage(EntityDamageEvent event) {
		if(!active) return;
		if(event instanceof EntityDamageByEntityEvent) {
			onDamageByEntity((EntityDamageByEntityEvent) event);
			return;
		}

		if(!(event.getEntity() instanceof LivingEntity)) return;
		LivingEntity entity = (LivingEntity) event.getEntity();

		ItemStack mainHand = ItemUtil.getMainHandItem(entity);
		ItemStack offHand = ItemUtil.getOffHandItem(entity);

		Map<Type, List<ItemStack>> defenseItems = new HashMap<>();
		List<ItemStack> armor = new ArrayList<>(), shield = new ArrayList<>(), bereHand = new ArrayList<>(), horseArmor = new ArrayList<>();

		for(ItemStack i : entity.getEquipment().getArmorContents()) { armor.add(i); }
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(player.isBlocking()) {
				if(ItemType.SHIELD.matchType(mainHand) || ItemType.SHIELD.matchType(offHand))
					shield.add(ItemType.SHIELD.matchType(mainHand) ? mainHand : offHand);
				else
					bereHand.add(!ItemUtil.isEmpty(mainHand) ? mainHand : offHand);
			}
		} else if(entity instanceof Horse) {
			horseArmor.add(((Horse) entity).getInventory().getArmor());
		}
		defenseItems.put(Type.ARMOR, armor);
		defenseItems.put(Type.SHIELD, shield);
		defenseItems.put(Type.IN_HAND, bereHand);
		defenseItems.put(Type.HORSE_ARMOR, horseArmor);

		for(Type type : defenseItems.keySet()) {
			for(ItemStack item : defenseItems.get(type)) {
				EOwnerDamagedEvent defenseEvent = new EOwnerDamagedEvent(item, entity, event.getDamage(), event.getCause(), type);
				defenseEvent.setCancelled(event.isCancelled());
				defenseEvent.setDamage(event.getDamage());
				{
					EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(defenseEvent.getItem()), defenseEvent);
				}
				event.setCancelled(defenseEvent.isCancelled());
				event.setDamage(defenseEvent.getDamage());
			}
		}
	}
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() == null || event.getDamager() == null)
			return;
		Entity damager = event.getDamager();

		if(damager instanceof Arrow) {
			damager = (Entity) ((Arrow) damager).getShooter();
			hitByArrow(event);
			if(event.isCancelled()) return;
		}

		if(damager instanceof LivingEntity) {
			LivingEntity owner = (LivingEntity) damager;

			EOwnerDamagesEntityEvent offenseEvent = new EOwnerDamagesEntityEvent(ItemUtil.getMainHandItem(owner), owner, event.getEntity(), event.getDamage(), event.getCause());
			offenseEvent.setCancelled(event.isCancelled());
			offenseEvent.setDamage(event.getDamage());
			{
				EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(offenseEvent.getItem()), offenseEvent);
			}
			event.setCancelled(offenseEvent.isCancelled());
			event.setDamage(offenseEvent.getDamage());
		}

		// Defensive
		if(!(event.getEntity() instanceof LivingEntity)) return;
		LivingEntity entity = (LivingEntity) event.getEntity();

		ItemStack mainHand = ItemUtil.getMainHandItem(entity);
		ItemStack offHand = ItemUtil.getOffHandItem(entity);

		Map<Type, List<ItemStack>> defenseItems = new HashMap<>();
		List<ItemStack> armor = new ArrayList<>(), shield = new ArrayList<>(), bereHand = new ArrayList<>(), horseArmor = new ArrayList<>();

		for(ItemStack i : entity.getEquipment().getArmorContents()) { armor.add(i); }
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(player.isBlocking()) {
				if(ItemType.SHIELD.matchType(mainHand) || ItemType.SHIELD.matchType(offHand))
					shield.add(ItemType.SHIELD.matchType(mainHand) ? mainHand : offHand);
				else
					bereHand.add(!ItemUtil.isEmpty(mainHand) ? mainHand : offHand);
			}
		} else if(entity instanceof Horse) {
			horseArmor.add(((Horse) entity).getInventory().getArmor());
		}
		defenseItems.put(Type.ARMOR, armor);
		defenseItems.put(Type.SHIELD, shield);
		defenseItems.put(Type.IN_HAND, bereHand);
		defenseItems.put(Type.HORSE_ARMOR, horseArmor);

		for(Type type : defenseItems.keySet()) {
			for(ItemStack item : defenseItems.get(type)) {
				EOwnerDamagedByEntityEvent defenseEvent = new EOwnerDamagedByEntityEvent(item, entity, event.getDamage(), event.getCause(), type, damager);
				defenseEvent.setCancelled(event.isCancelled());
				defenseEvent.setDamage(event.getDamage());
				{
					EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(defenseEvent.getItem()), defenseEvent);
				}
				event.setCancelled(defenseEvent.isCancelled());
				event.setDamage(defenseEvent.getDamage());
			}
		}
	}

	// ---------------------------------------------------------- //
	//                 EVENTS FOR CUSTOM BOWS                     //
	// ---------------------------------------------------------- //

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void bowShoot(EntityShootBowEvent event) {
		EBowShootEvent eEvent = new EBowShootEvent(event.getBow(), event.getEntity(),
		                                           event.getProjectile()
		);
		{
			for(Enchanted ench : EnchantmentRegistry.getEnchantments(event.getBow())) {
				ench.fireEvent(eEvent);
				EnchantmentRegistry.enchant(event.getProjectile(), ench.getEnchantment(), ench.getLvl(), true, false);
			}
		}
		event.setCancelled(eEvent.isCancelled());
	}

	public void hitByArrow(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Arrow)) return;
		if(!(event.getEntity() instanceof LivingEntity)) return;

		LivingEntity target = (LivingEntity) event.getEntity();
		Arrow arrow = (Arrow) event.getDamager();

		EArrowHitEvent eEvent = new EArrowHitEvent(target, arrow, event.getDamage());
		{
			EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getDamager()), eEvent);
		}
		event.setDamage(eEvent.getDamage());
		event.setCancelled(eEvent.isCancelled());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void arrowLandEvent(ProjectileHitEvent event) {
		if(!(event.getEntity() instanceof Arrow)) return;
		Arrow arrow = (Arrow) event.getEntity();

		EArrowLandEvent eEvent = new EArrowLandEvent(arrow);
		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getEntity()), eEvent);
	}

	// ---------------------------------------------------------- //
	//                     BLOCK EVENTS                           //
	// ---------------------------------------------------------- //

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDamageBlock(BlockDamageEvent event) {
		if(event.getBlock() == null || event.getBlock().getType() == null || event.getPlayer() == null || ItemUtil.isEmpty(event.getItemInHand()))
			return;

		EBlockDamageEvent e = new EBlockDamageEvent(event.getPlayer(), event.getItemInHand(), event.getBlock());
		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getItemInHand()), e);
		event.setCancelled(e.isCancelled());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock() == null || event.getBlock().getType() == null || event.getPlayer() == null) return;

		ItemStack item = ItemUtil.getMainHandItem(event.getPlayer());
		if(ItemUtil.isEmpty(item)) return;

		EBlockBreakEvent eEvent = new EBlockBreakEvent(item, event.getPlayer(), event.getBlock(), event.getExpToDrop());
		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(eEvent.getItem()), eEvent);
		event.setCancelled(eEvent.isCancelled());
		event.setExpToDrop(eEvent.getExpToDrop());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getBlock() == null || event.getBlock().getType() == null || event.getPlayer() == null || ItemUtil.isEmpty(event.getItemInHand()))
			return;

		EBlockPlaceEvent eEvent = new EBlockPlaceEvent(event.getItemInHand(), event.getPlayer(), event.getBlock(), event.getBlockAgainst(), event.getBlockPlaced(), event.getBlockReplacedState());
		eEvent.setBuild(event.canBuild());

		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(eEvent.getItem()), eEvent);

		event.setCancelled(eEvent.isCancelled());
		event.setBuild(eEvent.canBuild());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent event) {
		equip(event);
		if(event.getPlayer() == null || event.getAction() == null || ItemUtil.isEmpty(event.getItem())) return;

		HandType hT = CustomEnchantmentAPI.getInstance().getNSM().isHandMainHAnd(event) ? HandType.MAIN : HandType.OFF;
		EInteractEvent e = new EInteractEvent(event.getItem(), event.getPlayer(), event.getAction(), event.getBlockFace(), event.getClickedBlock(), hT);
		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getItem()), e);
		event.setCancelled(e.isCancelled());
	}

	// ---------------------------------------------------------- //
	//           INVENTORY CHANGE EVENTS(EQUIP,..)                //
	// ---------------------------------------------------------- //

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void inventoryClick(InventoryClickEvent event) {
		new EEquip(plugin.getServer().getPlayer(event.getWhoClicked().getName())).runTaskLater(plugin, 1);
	}

	public void equip(PlayerInteractEvent event) {
		new EEquip(event.getPlayer()).runTaskLater(plugin, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(PlayerItemBreakEvent event) {
		new EEquip(event.getPlayer()).runTaskLater(plugin, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDispense(BlockDispenseEvent e) {
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
	public void onItemDrop(PlayerDropItemEvent event) {
		new EEquip(event.getPlayer()).runTaskLater(plugin, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onItemPickUp(PlayerPickupItemEvent event) {
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

		for(ItemStack item : entity.getEquipment().getArmorContents()) {
			EUnequipEvent eEvent = new EUnequipEvent(item, entity);
			EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
		}
	}
}
