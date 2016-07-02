package adx.audioxd.customenchantmentapi.listeners.extra;


import adx.audioxd.customenchantmentapi.listeners.CEAPIListenerUtils;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Hashtable;
import java.util.Map;

public class EEquip extends BukkitRunnable {
	static Map<String, InventoryContents> equipment = new Hashtable<>();
	public static void loadPlayer(Player player) {
		equipment.put(player.getName(), new InventoryContents(player));
	}
	public static void clearPlayer(Player player) {
		equipment.remove(player.getName());
	}
	public static void clear() {
		equipment.clear();
	}


	Player player;
	public EEquip(Player player) {
		this.player = player;
	}

	public void run() {
		InventoryContents nowI = new InventoryContents(player);
		ItemStack[] nowArmorContents = nowI.armorContents;
		ItemStack nowMainHand = nowI.mainHand;
		ItemStack nowOffHand = nowI.offHand;

		InventoryContents prewI = equipment.get(player.getName());
		ItemStack[] prewArmorContents = prewI.armorContents;
		ItemStack prewMainHand = prewI.mainHand;
		ItemStack prewOffHand = prewI.offHand;

		try {
			for(int i = 0; i < nowArmorContents.length; i++) {
				if(ItemUtil.isEmpty(nowArmorContents[i]) && (prewArmorContents != null && !ItemUtil.isEmpty(prewArmorContents[i])))
					CEAPIListenerUtils.unenquipt(player, prewArmorContents[i]);
				else if(!ItemUtil.isEmpty(nowArmorContents[i]) && (prewArmorContents == null || ItemUtil.isEmpty(prewArmorContents[i])))
					CEAPIListenerUtils.enquipt(player, nowArmorContents[i]);
				else if(prewArmorContents == null)
				    /* do nothing */;
				else if(!nowArmorContents[i].toString().equalsIgnoreCase(prewArmorContents[i].toString())) {
					CEAPIListenerUtils.unenquipt(player, prewArmorContents[i]);
					CEAPIListenerUtils.enquipt(player, nowArmorContents[i]);
				}
			}

			// Main hand
			if(ItemUtil.isEmpty(nowMainHand) && !ItemUtil.isEmpty(prewMainHand))
				CEAPIListenerUtils.itemNotInMainHand(player, prewMainHand);
			else if(!ItemUtil.isEmpty(nowMainHand) && ItemUtil.isEmpty(prewMainHand))
				CEAPIListenerUtils.itemInMainHand(player, nowMainHand);
			else if(prewMainHand == null)
				/* do nothing */;
			else if(!nowMainHand.toString().equalsIgnoreCase(prewMainHand.toString())) {
				CEAPIListenerUtils.itemNotInMainHand(player, prewMainHand);
				CEAPIListenerUtils.itemInMainHand(player, nowMainHand);
			}

			// Off hand
			if(ItemUtil.isEmpty(nowOffHand) && !ItemUtil.isEmpty(prewOffHand))
				CEAPIListenerUtils.itemNotInOffHand(player, prewOffHand);
			else if(!ItemUtil.isEmpty(nowOffHand) && ItemUtil.isEmpty(prewOffHand))
				CEAPIListenerUtils.itemInOffHand(player, nowOffHand);
			else if(prewOffHand == null)
				/* do nothing */;
			else if(!nowOffHand.toString().equalsIgnoreCase(prewOffHand.toString())) {
				CEAPIListenerUtils.itemNotInOffHand(player, prewOffHand);
				CEAPIListenerUtils.itemInOffHand(player, nowOffHand);
			}
		} catch(Exception e) { }
		equipment.put(player.getName(), nowI);
	}

	private static class InventoryContents {
		ItemStack[] armorContents;
		ItemStack mainHand;
		ItemStack offHand;

		public InventoryContents(LivingEntity entity) {
			this(entity.getEquipment().getArmorContents(), ItemUtil.getMainHandItem(entity), ItemUtil.getOffHandItem(entity));
		}

		public InventoryContents(ItemStack[] armorContents, ItemStack mainHand, ItemStack offHand) {
			this.armorContents = armorContents;
			this.mainHand = mainHand;
			this.offHand = offHand;
		}
	}
}
