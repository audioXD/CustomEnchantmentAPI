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
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
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

	public DamageListener(CustomEnchantmentAPI plugin) {
		super(plugin);
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
}
