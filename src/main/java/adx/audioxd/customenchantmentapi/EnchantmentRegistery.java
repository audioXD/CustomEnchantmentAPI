package adx.audioxd.customenchantmentapi;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import adx.audioxd.customenchantmentapi.utils.RomanNumeral;

public class EnchantmentRegistery {
	private static final Map<Plugin, Map<String, Enchantment>> enchantmentsMap = new HashMap<Plugin, Map<String, Enchantment>>();

	private static final Set<Enchantment> enchantments = new HashSet<Enchantment>();
	private static volatile Enchantment[] backedEnchantments = null;

	private EnchantmentRegistery() {
	}

	/**
	 * This method registers the Enchantment.
	 *
	 * @param plugin      The Plugin that registers the Enchantment.
	 * @param enchantment The Enchantment that is being registered.
	 * @return If the Enchantment has been registered.(Like if some other plugin
	 * has a Enchantment with the same display name)
	 */
	public static synchronized boolean register(Plugin plugin, Enchantment enchantment) {
		if (plugin == null || enchantment == null) return false;
		if (enchantments.add(enchantment)) {
			Map<String, Enchantment> enchs = enchantmentsMap.containsKey(plugin) ?
					enchantmentsMap.get(plugin) :
					new HashMap<String, Enchantment>();

			enchs.put(getID(enchantment), enchantment);

			enchantmentsMap.put(plugin, enchs);
			backedEnchantments = null;
			return true;
		}

		return false;
	}

	/**
	 * Unregisters a Enchantment.
	 *
	 * @param plugin      The plugin that registered the Enchantment.
	 * @param enchantment The Enchantment.
	 * @return If the Enchantment has ben unregistered.
	 */
	public static synchronized boolean unregister(Plugin plugin, Enchantment enchantment) {
		if (plugin == null || enchantment == null) return false;

		if (enchantments.remove(enchantment)) {
			if (enchantmentsMap.containsKey(plugin)) {
				Map<String, Enchantment> enchs = enchantmentsMap.get(plugin);
				if (enchs.containsKey(getID(enchantment))) enchs.remove(getID(enchantment));
				if (enchs.isEmpty()) enchantmentsMap.remove(plugin);
			}
			backedEnchantments = null;
			return true;
		}

		return false;
	}

	/**
	 * Unregisters all Enchantments of a Plugin.
	 *
	 * @param plugin If it unregistered.
	 */
	public static synchronized void unregisterAll(Plugin plugin) {
		if (plugin == null) return;

		if (enchantmentsMap.containsKey(plugin)) {
			for (Enchantment en : enchantmentsMap.get(plugin).values()) {
				if (enchantments.remove(en)) {
					backedEnchantments = null;
				}
			}
			enchantmentsMap.remove(plugin);
		}
	}

	private static String getID(Enchantment ench) {
		return ChatColor.stripColor(ench.getName().toUpperCase()).replace(" ", "_");
	}

	/**
	 * Gets all Enchantments registered in a HashMap.
	 *
	 * @return Returns a Map.
	 */
	public static synchronized Map<Plugin, Map<String, Enchantment>> getEnchantments() {
		return enchantmentsMap;
	}

	/**
	 * returns a array of Enchantment[].
	 *
	 * @return Returns a Enchantment[].
	 */
	public static synchronized Enchantment[] geEnchantmentsArray() {
		if (backedEnchantments != null) return backedEnchantments;
		return bake();
	}

	private static Enchantment[] bake() {
		Enchantment[] baked = backedEnchantments;
		if (baked == null) {
			// Set -> array
			synchronized (EnchantmentRegistery.class) {
				if ((baked = backedEnchantments) == null) {
					baked = enchantments.toArray(new Enchantment[enchantments.size()]);
					Arrays.sort(baked);
					backedEnchantments = baked;

				}
			}

		}
		return baked;
	}

	/**
	 * Resets all Enchantments that have been registered.
	 */
	public static synchronized void reset() {
		enchantments.clear();
		enchantmentsMap.clear();
		backedEnchantments = null;
	}

	/********************************************************************/
	/* Some function for easy use. */
	/********************************************************************/

	/**
	 * Gets the Enchantment from the ID.
	 *
	 * @param id The id(case sensitive) given from getID().
	 * @return Returns the Enchantment or null.
	 */
	public static synchronized Enchantment getFromID(String id) {
		if (id == null) return null;
		if (id.trim().length() == 0) return null;

		String[] data = id.split("\\:");
		if (data.length < 2) return null;
		String plugin = data[0];
		String ench = data[1];
		Plugin plu = Bukkit.getPluginManager().getPlugin(plugin);

		if (!enchantmentsMap.containsKey(plu)) return null;

		Map<String, Enchantment> data2 = enchantmentsMap.get(plu);
		if (data2 == null) return null;
		if (data2.isEmpty()) return null;
		if (data2.containsKey(ench.toUpperCase())) {
			return data2.get(ench.toUpperCase());
		}
		return null;
	}

	/**
	 * Returns a ID. That can be used from getFromID().
	 *
	 * @param plugin      The Plugin that registered the Encahntment.
	 * @param enchantment The Enchantment.
	 * @return The ID
	 */
	public static String getID(Plugin plugin, Enchantment enchantment) {
		if (plugin == null) return null;
		if (enchantment == null) return null;
		return plugin.getName() + ":" + getID(enchantment);
	}

	// ******************************************************\\
	// METHODS FOR ENCHANTING \\
	// ******************************************************\\

	/**
	 * Unenchants a Enchantment from a Item
	 *
	 * @param item        The item that you want to unenchant a Enchantment from.
	 * @param enchantment The Enchantment that you want to unenchant.
	 * @return If it unenchanted(Like if the Enchantment didn't exist on the
	 * item).
	 */
	public static boolean unenchant(ItemStack item, Enchantment enchantment) {
		if (ItemUtil.isEmpty(item)) return false;
		if (enchantment == null) return false;

		if (!item.hasItemMeta()) return false;
		ItemMeta data = item.getItemMeta();
		if (!data.hasLore()) return false;
		List<String> lore = data.getLore();

		for (int i = 0; i < lore.size(); i++) {
			String line = lore.get(i);
			if (enchantment.hasCustomEnchantment(line)) {
				lore.remove(i);
				data.setLore(lore);
				item.setItemMeta(data);
				CustomEnchantmentAPI.getInstace().getTLogger().info("Unenchanted item with: " + line);
				return true;
			}
		}

		return false;
	}

	/**
	 * Enchantnts a plugin with a Enchantment.
	 *
	 * @param item                     The item you want to enchant.
	 * @param enchantment              The Enchantment you want to enchant on a item.
	 * @param level                    The level of the enchantment.
	 * @param override                 If it overrides the current enchantment.
	 * @param override_if_larger_level If it overrides if there's a larger level.
	 * @return If the enchant method was successful.
	 */
	public static boolean enchant(ItemStack item, Enchantment enchantment, int level, boolean override,
			boolean override_if_larger_level) {
		if (ItemUtil.isEmpty(item)) return false;
		if (enchantment == null) return false;
		if (!enchantment.getType().matchType(item)) return false;
		if (enchantment.getMaxLvl() < level) level = enchantment.getMaxLvl();

		boolean flag = false;

		ItemMeta data = item.getItemMeta();
		{
			List<String> lore = data.hasLore() ? data.getLore() : new ArrayList<String>();
			{
				if (!lore.contains(enchantment.getDisplay(level))) {
					int maxLvl = 0;
					for (int i = lore.size() - 1; i >= 0; i--) {
						String line = lore.get(i);
						if (enchantment.hasCustomEnchantment(line)) {
							int lvl = RomanNumeral.getIntFromRoman(line.substring(line.lastIndexOf(" ") + 1));
							if (lvl < level || override) lore.remove(i);
							if (lvl > maxLvl) maxLvl = lvl;
						}
					}
					if ((level > maxLvl && override_if_larger_level) || override) {
						lore.add(0, enchantment.getDisplay(level));
						CustomEnchantmentAPI.getInstace().getTLogger()
								.info("Enchanted item with: " + enchantment.getDisplay(level));
						flag = true;
					}
				}
			}
			data.setLore(lore);
		}
		item.setItemMeta(data);
		return flag;
	}

	/**
	 * Gets a array of Enchanted Enchantments on the item.
	 *
	 * @param item The item you want to list a array of Enchanted.
	 * @return A array of Enchanted Enchantments.
	 */
	public static synchronized Enchanted[] getEnchantments(ItemStack item) {
		List<Enchanted> res = new ArrayList<Enchanted>();
		if (ItemUtil.isEmpty(item)) return res.toArray(new Enchanted[res.size()]);
		if (!item.hasItemMeta()) return res.toArray(new Enchanted[res.size()]);
		ItemMeta data = item.getItemMeta();
		if (!data.hasLore()) return res.toArray(new Enchanted[res.size()]);

		for (String line : data.getLore()) {
			if (line == null) continue;
			if (line.equalsIgnoreCase("")) continue;

			for (Enchantment ench : bake()) {
				if (ench.hasCustomEnchantment(line)) {
					int lvl = RomanNumeral.getIntFromRoman(line.substring(line.lastIndexOf(" ") + 1));
					res.add(new Enchanted(lvl, ench));
				}
			}
		}

		return res.toArray(new Enchanted[res.size()]);
	}
}
