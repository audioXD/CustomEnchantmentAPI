package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistery;
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

	CEPLListener(CustomEnchantmentAPI plugin) {
		this.plugin = plugin;
	}

	public static void unenquipt(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			EUnequipEvent eevent = new EUnequipEvent(ench.getLvl(), item, owner);
			ench.fireEvent(eevent);
		}

		for(ItemStack i : owner.getEquipment().getArmorContents()) {
			if(ItemUtil.isEmpty(i)) continue;
			if(i.equals(item)) continue;

			enquipt(owner, i);
		}
	}

	public static Enchanted[] getEnchantments(ItemStack item) {
		return EnchantmentRegistery.getEnchantments(item);
	}

	public static void enquipt(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			EEquipEvent eevent = new EEquipEvent(ench.getLvl(), item, owner);
			{
				ench.fireEvent(eevent);
			}
		}
	}

	public static void itemNotInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			ench.fireEvent(new EItemNotInHandEvent(ench.getLvl(), item, owner, handType));
			if(HandType.MAIN.equals(handType)) ench.fireEvent(new EItemNotInMainHandEvent(ench.getLvl(), item, owner));
			if(HandType.OFF.equals(handType)) ench.fireEvent(new EItemNotInOffHandEvent(ench.getLvl(), item, owner));
		}

		if(HandType.MAIN.equals(handType)) itemInOffHand(owner, ItemUtil.getOffHandItem(owner));
		if(HandType.OFF.equals(handType)) itemInMainHand(owner, ItemUtil.getMainHandItem(owner));
	}

	public static void itemInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			ench.fireEvent(new EItemInOffHandEvent(ench.getLvl(), item, owner));
		}
	}

	public static void itemInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			ench.fireEvent(new EItemInMainHandEvent(ench.getLvl(), item, owner));
		}
	}

	public static void itemInHand(LivingEntity owner, ItemStack item, HandType handType) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			ench.fireEvent(new EItemInHandEvent(ench.getLvl(), item, owner, handType));
			if(HandType.MAIN.equals(handType)) ench.fireEvent(new EItemInMainHandEvent(ench.getLvl(), item, owner));
			if(HandType.OFF.equals(handType)) ench.fireEvent(new EItemInOffHandEvent(ench.getLvl(), item, owner));
		}
	}

	public static void itemNotInMainHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			ench.fireEvent(new EItemNotInMainHandEvent(ench.getLvl(), item, owner));
		}
	}

	public static void itemNotInOffHand(LivingEntity owner, ItemStack item) {
		if(owner == null || ItemUtil.isEmpty(item)) return;

		for(Enchanted ench : getEnchantments(item)) {
			ench.fireEvent(new EItemNotInOffHandEvent(ench.getLvl(), item, owner));
		}
	}
}
