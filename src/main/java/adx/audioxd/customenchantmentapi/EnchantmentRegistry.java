package adx.audioxd.customenchantmentapi;


import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.events.enchant.EEnchantEvent;
import adx.audioxd.customenchantmentapi.events.enchant.EUnenchantEvent;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import adx.audioxd.customenchantmentapi.utils.RomanNumeral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class EnchantmentRegistry {
	// Global fields
	private static final Map<Plugin, Map<String, Enchantment>> enchantmentsMap = new HashMap<>();

	private static final Set<RegisteredEnchantment> enchantments = new HashSet<>();
	private static volatile Enchantment[] backedActiveEnchantments = null;
// End of Global Fields

	// Constructor
	private EnchantmentRegistry() {}

	/**
	 * This method registers the Enchantment.
	 *
	 * @param plugin      The Plugin that registers the Enchantment.
	 * @param enchantment The Enchantment that is being registered.
	 * @return If the Enchantment has been registered.(Like if some other plugin
	 * has a Enchantment with the same display name)
	 */
	public static synchronized boolean register(Plugin plugin, Enchantment enchantment) {
		if(plugin == null || enchantment == null) return false;
		return register(new RegisteredEnchantment(enchantment, plugin));
	}

	/**
	 * This method registers the Enchantment.
	 *
	 * @param registeredEnchantment A RegisteredEnchantment.
	 * @return If the Enchantment has been registered.(Like if some other plugin
	 * has a Enchantment with the same display name)
	 */
	public static synchronized boolean register(RegisteredEnchantment registeredEnchantment) {
		if(registeredEnchantment == null) return false;

		Plugin plugin = registeredEnchantment.getPlugin();
		Enchantment enchantment = registeredEnchantment.getEnchantment();
		String enchMapID = getEnchantmentsMapID(enchantment);

		Map<String, Enchantment> enchs = enchantmentsMap.containsKey(plugin) ?
				enchantmentsMap.get(plugin) :
				new HashMap<>();

		if(!enchs.containsKey(enchMapID)) {
			if(enchantments.add(registeredEnchantment)) {
				enchs.put(enchMapID, enchantment);
				enchantmentsMap.put(plugin, enchs);
				backedActiveEnchantments = null;

				registeredEnchantment.setActive(
						CustomEnchantmentAPI.getInstance().getEnchantmentsConfig().isActive(
								registeredEnchantment.getPlugin(),
								registeredEnchantment.getEnchantment()
						)
				);
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the name without Color in UpperCase with spaces replaced with '_' (In other words the ID).
	 *
	 * @param enchantment The enchantment That you want to get the ID from.
	 * @return The ID.
	 */
	public static String getEnchantmentsMapID(Enchantment enchantment) {
		return ChatColor.stripColor(enchantment.getName().toUpperCase()).replace(" ", "_");
	}

	/**
	 * Unregisters a Enchantment.
	 *
	 * @param plugin      The plugin that registered the Enchantment.
	 * @param enchantment The Enchantment.
	 * @return If the Enchantment has ben unregistered.
	 */
	public static synchronized boolean unregister(Plugin plugin, Enchantment enchantment) {
		if(plugin == null || enchantment == null) return false;
		return unregister(new RegisteredEnchantment(enchantment, plugin));
	}

	/**
	 * Unregisters a Enchantment.
	 *
	 * @param registeredEnchantment The Registered Enchantment.
	 * @return If the Enchantment has ben unregistered.
	 */
	public static synchronized boolean unregister(RegisteredEnchantment registeredEnchantment) {
		if(registeredEnchantment == null) return false;

		Plugin plugin = registeredEnchantment.getPlugin();
		Enchantment enchantment = registeredEnchantment.getEnchantment();
		String enchMapID = getEnchantmentsMapID(enchantment);

		if(enchantments.remove(registeredEnchantment)) {
			if(enchantmentsMap.containsKey(plugin)) {
				Map<String, Enchantment> enchs = enchantmentsMap.get(plugin);

				if(enchs.containsKey(enchMapID))
					enchs.remove(enchMapID);

				if(enchs.isEmpty()) enchantmentsMap.remove(plugin);
			}
			backedActiveEnchantments = null;
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
		if(plugin == null) return;

		if(enchantmentsMap.containsKey(plugin)) {
			for(Enchantment en : enchantmentsMap.get(plugin).values()) {

				if(enchantments.remove(new RegisteredEnchantment(en, plugin))) {
					backedActiveEnchantments = null;
				}
			}
			enchantmentsMap.remove(plugin);
		}
	}

	/**
	 * Resets all Enchantments that have been registered.
	 */
	public static synchronized void reset() {
		enchantments.clear();
		enchantmentsMap.clear();
		backedActiveEnchantments = null;
	}

	/**
	 * Gets the Enchantment from the ID.
	 *
	 * @param id The id(case sensitive) given from getID().
	 * @return Returns the Enchantment or null.
	 */
	public static synchronized Enchantment getFromID(String id) {
		if(id == null) return null;
		if(id.trim().length() == 0) return null;

		String[] data = id.split(":");
		if(data.length < 2) return null;
		String plugin = data[0];
		String ench = data[1];
		Plugin plu = Bukkit.getPluginManager().getPlugin(plugin);

		if(!enchantmentsMap.containsKey(plu)) return null;

		Map<String, Enchantment> data2 = enchantmentsMap.get(plu);
		if(data2 == null) return null;
		if(data2.isEmpty()) return null;
		if(data2.containsKey(ench.toUpperCase())) {
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
		if(plugin == null) return null;
		if(enchantment == null) return null;
		return plugin.getName() + ":" + getEnchantmentsMapID(enchantment);
	}


	/**
	 * Unenchants a Enchantment from a Item
	 *
	 * @param item        The item that you want to unenchant a Enchantment from.
	 * @param enchantment The Enchantment that you want to unenchant.
	 * @return If it unenchanted(Like if the Enchantment didn't exist on the
	 * item).
	 */
	public static boolean unenchant(ItemStack item, Enchantment enchantment) {
		if(ItemUtil.isEmpty(item)) return false;
		if(enchantment == null) return false;

		if(!item.hasItemMeta()) return false;
		ItemMeta data = item.getItemMeta();
		if(!data.hasLore()) return false;
		List<String> lore = data.getLore();
		if(lore.isEmpty()) return false;

		List<String> newLore = new ArrayList<String>();

		for(String line : lore) {
			if(!enchantment.hasCustomEnchantment(line)) newLore.add(line);
		}
		if(lore.equals(newLore)) return false;

		data.setLore(newLore);
		item.setItemMeta(data);
		CustomEnchantmentAPI.getCEAPILogger().info("Unenchanted item with: " + enchantment.getDisplay(""));
		enchantment.fireEvent(new EUnenchantEvent(item));
		return true;
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
		if(ItemUtil.isEmpty(item)) return false;
		if(enchantment == null) return false;
		if(!enchantment.getType().matchType(item)) return false;
		if(enchantment.getMaxLvl() < level) level = enchantment.getMaxLvl();

		boolean flag = false;

		ItemMeta data = item.getItemMeta();
		{
			List<String> lore = data.hasLore() ? data.getLore() : new ArrayList<String>();
			{
				if(!lore.contains(enchantment.getDisplay(level))) {
					int maxLvl = 0;
					for(int i = lore.size() - 1; i >= 0; i--) {
						String line = lore.get(i);
						if(enchantment.hasCustomEnchantment(line)) {
							int lvl = RomanNumeral.getIntFromRoman(line.substring(line.lastIndexOf(" ") + 1));
							if(lvl < level || override) lore.remove(i);
							if(lvl > maxLvl) maxLvl = lvl;
						}
					}
					if((level > maxLvl && override_if_larger_level) || override) {
						lore.add(0, enchantment.getDisplay(level));
						CustomEnchantmentAPI.getCEAPILogger()
								.info("Enchanted item with: " + enchantment.getDisplay(level));
						flag = true;
					}
				}
			}
			data.setLore(lore);
		}
		item.setItemMeta(data);
		if(flag) enchantment.fireEvent(new EEnchantEvent(level, item));
		return flag;
	}

	/**
	 * Gets a array of Enchanted Enchantments on the item.
	 *
	 * @param item The item you want to list a array of Enchanted.
	 * @return A array of Enchanted Enchantments.
	 */
	public static synchronized Enchanted[] getEnchantments(ItemStack item) {
		List<Enchanted> res = new ArrayList<>();
		if(ItemUtil.isEmpty(item)) return res.toArray(new Enchanted[res.size()]);
		if(!item.hasItemMeta()) return res.toArray(new Enchanted[res.size()]);
		ItemMeta data = item.getItemMeta();
		if(!data.hasLore()) return res.toArray(new Enchanted[res.size()]);

		for(String line : data.getLore()) {
			if(line == null) continue;
			if(line.equalsIgnoreCase("")) continue;

			for(Enchantment ench : bake()) {
				if(ench.hasCustomEnchantment(line)) {
					int lvl = RomanNumeral.getIntFromRoman(line.substring(line.lastIndexOf(" ") + 1));
					res.add(new Enchanted(lvl, ench));
				}
			}
		}

		return res.toArray(new Enchanted[res.size()]);
	}

	/**
	 * This method in a bake method for synchronization
	 *
	 * @return Returns a list of Enchantments that are thread safe.
	 */
	private static Enchantment[] bake() {
		Enchantment[] baked = backedActiveEnchantments;
		if(baked == null) {
			// Set -> array
			synchronized(EnchantmentRegistry.class) {
				if((baked = backedActiveEnchantments) == null) {
					Map<String, Enchantment> active = new HashMap<>();

					for(RegisteredEnchantment en : enchantments) {
						if(CustomEnchantmentAPI.getInstance().getEnchantmentsConfig().isActive(
								en.getPlugin(),
								en.getEnchantment()
						)) {
							if(active.containsKey(en.getEnchantment().getDisplay(""))) {
								en.setActive(false);
								continue;
							}

							active.put(en.getEnchantment().getDisplay(""), en.getEnchantment());
						}
					}

					baked = active.values().toArray(new Enchantment[active.values().size()]);
					Arrays.sort(baked);
					backedActiveEnchantments = baked;

				}
			}

		}
		return baked;
	}

	/**
	 * Rebuild the Enchantments Array.
	 */
	public synchronized static void rebuildEnchantmentsArray() {
		if(backedActiveEnchantments != null)
			backedActiveEnchantments = null;
	}
// Getters

	/**
	 * returns a array of Enchantment[].
	 *
	 * @return Returns a Enchantment[].
	 */
	public static synchronized Enchantment[] getEnchantmentsArray() {
		if(backedActiveEnchantments != null) return backedActiveEnchantments;
		return bake();
	}

	/**
	 * Gets all Enchantments registered in a HashMap.
	 *
	 * @return Returns a Map.
	 */
	public static synchronized Map<Plugin, Map<String, Enchantment>> getEnchantments() {
		return enchantmentsMap;
	}
}
