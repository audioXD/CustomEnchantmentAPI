package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedByEntityEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagesEntityEvent;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent.Type;

public class DamageListener extends CEAPIListenerUtils {

	// ---------------------------------------------------------- //
	//                      CONSTRUCTOR                           //
	// ---------------------------------------------------------- //

	private boolean excuse = false;

	public DamageListener(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	// ---------------------------------------------------------- //
	//                      DAMAGE EVENTS                         //
	// ---------------------------------------------------------- //

	public static boolean active = true;
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if(!active || event.getEntity() == null || event.getDamager() == null)
			return;

		if(event.getDamager() instanceof LivingEntity) {
			LivingEntity owner = (LivingEntity) event.getDamager();

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
		Entity damager = event.getDamager();

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
					bereHand.add(ItemType.SHIELD.matchType(mainHand) ? mainHand : offHand);
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

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPureDamage(EntityDamageEvent event) {
		if(!active || event instanceof EntityDamageByEntityEvent || !(event.getEntity() instanceof LivingEntity))
			return;
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
					bereHand.add(ItemType.SHIELD.matchType(mainHand) ? mainHand : offHand);
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
}
