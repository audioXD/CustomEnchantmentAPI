package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.events.inventory.EEquipEvent;
import adx.audioxd.customenchantmentapi.events.inventory.EUnequipEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.*;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class CEPLListener implements Listener {
	protected CustomEnchantmentAPI plugin;

	// Constructor
	CEPLListener(CustomEnchantmentAPI plugin) {
		this.plugin = plugin;
	}

	public static void unenquipt(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		EUnequipEvent eEvent = new EUnequipEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);

		for(ItemStack i : owner.getEquipment().getArmorContents()) {
			if(ItemUtil.isEmpty(i)) continue;
			if(i.equals(item)) continue;

			enquipt(owner, i);
		}
	}

	public static Enchanted[] getEnchantments(ItemStack item) {
		return EnchantmentRegistry.getEnchantments(item);
	}

	public static void enquipt(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		EEquipEvent eEvent = new EEquipEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
	}

	public static void itemNotInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		EItemNotInHandEvent eItemNotInHandEvent = new EItemNotInHandEvent(item, owner, handType);
		EItemNotInMainHandEvent eItemNotInMainHandEvent = new EItemNotInMainHandEvent(item, owner);
		EItemNotInOffHandEvent eItemNotInOffHandEvent = new EItemNotInOffHandEvent(item, owner);

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(eItemNotInHandEvent);
			if(HandType.MAIN.equals(handType))
				enchanted.fireEvent(eItemNotInMainHandEvent);
			if(HandType.OFF.equals(handType))
				enchanted.fireEvent(eItemNotInOffHandEvent);
		}

		if(HandType.MAIN.equals(handType)) itemInOffHand(owner, ItemUtil.getOffHandItem(owner));
		if(HandType.OFF.equals(handType)) itemInMainHand(owner, ItemUtil.getMainHandItem(owner));
	}

	public static void itemInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		EItemInOffHandEvent eEvent = new EItemInOffHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
	}

	public static void itemInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		EItemInMainHandEvent eEvent = new EItemInMainHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
	}

	public static void itemInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		EItemHandEvent eItemHandEvent = new EItemInHandEvent(item, owner, handType);
		EItemInMainHandEvent eItemInMainHandEvent = new EItemInMainHandEvent(item, owner);
		EItemInOffHandEvent eItemInOffHandEvent = new EItemInOffHandEvent(item, owner);

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(eItemHandEvent);
			if(HandType.MAIN.equals(handType))
				enchanted.fireEvent(eItemInMainHandEvent);
			if(HandType.OFF.equals(handType))
				enchanted.fireEvent(eItemInOffHandEvent);
		}
	}

	public static void itemNotInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		EItemNotInMainHandEvent eEvent = new EItemNotInMainHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
	}

	public static void itemNotInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;
		EItemNotInOffHandEvent eEvent = new EItemNotInOffHandEvent(item, owner);
		EnchantmentRegistry.fireEvents(getEnchantments(item), eEvent);
	}
}
