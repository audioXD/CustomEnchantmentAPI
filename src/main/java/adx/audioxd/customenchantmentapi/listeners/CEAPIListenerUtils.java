package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.events.inventory.EEquipEvent;
import adx.audioxd.customenchantmentapi.events.inventory.EUnequipEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInMainHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInOffHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemNotInMainHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemNotInOffHandEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static adx.audioxd.customenchantmentapi.EnchantmentRegistry.getEnchantments;

public abstract class CEAPIListenerUtils implements Listener {
	protected CustomEnchantmentAPI plugin;

	public CustomEnchantmentAPI getPlugin() { return plugin; }

	// ---------------------------------------------------------- //
	//                      CONSTRUCTOR                           //
	// ---------------------------------------------------------- //

	CEAPIListenerUtils(CustomEnchantmentAPI plugin) {
		this.plugin = plugin;
	}

	// ---------------------------------------------------------- //
	//               METHODS FOR EASE OF USE                      //
	// ---------------------------------------------------------- //

	public static boolean enquipt(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EEquipEvent eEvent = new EEquipEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
		return !eEvent.isCancelled();
	}
	public static boolean unenquipt(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EUnequipEvent eEvent = new EUnequipEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);

		for(ItemStack i : owner.getEquipment().getArmorContents()) {
			if(ItemUtil.isEmpty(i)) continue;
			if(i.toString().equalsIgnoreCase(item.toString())) continue;

			enquipt(owner, i);
		}
		return eEvent.isCancelled();
	}

	public static boolean itemInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EItemInMainHandEvent eItemInMainHandEvent = new EItemInMainHandEvent(item, owner);
		EItemInOffHandEvent eItemInOffHandEvent = new EItemInOffHandEvent(item, owner);

		if(HandType.MAIN.equals(handType))
			EnchantmentRegistry.fireEvents(getEnchantments(item), eItemInMainHandEvent);
		else if(HandType.OFF.equals(handType))
			EnchantmentRegistry.fireEvents(getEnchantments(item), eItemInOffHandEvent);

		return !eItemInMainHandEvent.isCancelled() && !eItemInOffHandEvent.isCancelled();
	}
	public static boolean itemNotInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EItemNotInMainHandEvent eItemNotInMainHandEvent = new EItemNotInMainHandEvent(item, owner);
		EItemNotInOffHandEvent eItemNotInOffHandEvent = new EItemNotInOffHandEvent(item, owner);

		if(HandType.MAIN.equals(handType))
			EnchantmentRegistry.fireEvents(getEnchantments(item), eItemNotInMainHandEvent);
		else if(HandType.OFF.equals(handType))
			EnchantmentRegistry.fireEvents(getEnchantments(item), eItemNotInOffHandEvent);

		if(eItemNotInMainHandEvent.isCancelled() || eItemNotInOffHandEvent.isCancelled()) return false;

		if(HandType.MAIN.equals(handType)) return itemInOffHand(owner, ItemUtil.getOffHandItem(owner));
		if(HandType.OFF.equals(handType)) return itemInMainHand(owner, ItemUtil.getMainHandItem(owner));
		return true;
	}

	public static boolean itemInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EItemInOffHandEvent eEvent = new EItemInOffHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
		return !eEvent.isCancelled();
	}
	public static boolean itemNotInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EItemNotInOffHandEvent eEvent = new EItemNotInOffHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
		return !eEvent.isCancelled();
	}

	public static boolean itemInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EItemInMainHandEvent eEvent = new EItemInMainHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
		return !eEvent.isCancelled();
	}
	public static boolean itemNotInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return true;

		EItemNotInMainHandEvent eEvent = new EItemNotInMainHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
		return !eEvent.isCancelled();
	}
}
