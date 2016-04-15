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

		for(Enchanted enchanted : getEnchantments(item)) {
			EUnequipEvent eEvent = new EUnequipEvent(enchanted.getLvl(), item, owner);
			enchanted.fireEvent(eEvent);
		}

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

		for(Enchanted enchanted : getEnchantments(item)) {
			EEquipEvent eEvent = new EEquipEvent(enchanted.getLvl(), item, owner);
			{
				enchanted.fireEvent(eEvent);
			}
		}
	}

	public static void itemNotInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(new EItemNotInHandEvent(enchanted.getLvl(), item, owner, handType));
			if(HandType.MAIN.equals(handType))
				enchanted.fireEvent(new EItemNotInMainHandEvent(enchanted.getLvl(), item, owner));
			if(HandType.OFF.equals(handType))
				enchanted.fireEvent(new EItemNotInOffHandEvent(enchanted.getLvl(), item, owner));
		}

		if(HandType.MAIN.equals(handType)) itemInOffHand(owner, ItemUtil.getOffHandItem(owner));
		if(HandType.OFF.equals(handType)) itemInMainHand(owner, ItemUtil.getMainHandItem(owner));
	}

	public static void itemInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(new EItemInOffHandEvent(enchanted.getLvl(), item, owner));
		}
	}

	public static void itemInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(new EItemInMainHandEvent(enchanted.getLvl(), item, owner));
		}
	}

	public static void itemInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(new EItemInHandEvent(enchanted.getLvl(), item, owner, handType));
			if(HandType.MAIN.equals(handType))
				enchanted.fireEvent(new EItemInMainHandEvent(enchanted.getLvl(), item, owner));
			if(HandType.OFF.equals(handType))
				enchanted.fireEvent(new EItemInOffHandEvent(enchanted.getLvl(), item, owner));
		}
	}

	public static void itemNotInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(new EItemNotInMainHandEvent(enchanted.getLvl(), item, owner));
		}
	}

	public static void itemNotInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted enchanted : getEnchantments(item)) {
			enchanted.fireEvent(new EItemNotInOffHandEvent(enchanted.getLvl(), item, owner));
		}
	}
}
