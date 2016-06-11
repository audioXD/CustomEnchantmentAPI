package adx.audioxd.customenchantmentapi.config;


import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.config.option.BooleanOption;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentsConfig extends Config {
	private Map<String, BooleanOption> options = new HashMap<>();


	// Constructor
	public EnchantmentsConfig(Plugin plugin) {
		super(plugin, "Enchantments");
	}


	@Override
	public void onLoad(YamlConfiguration config) {
		for(String plugin : config.getConfigurationSection("").getValues(false).keySet()) {
			for(String enchantment : config.getConfigurationSection(plugin).getValues(false).keySet()) {
				String path = plugin + "." + enchantment;

				BooleanOption ench = options.containsKey(path) ? options.get(path) : new BooleanOption(path, true);
				ench.loadIfExist(this);

				if(!options.containsKey(path)) {
					options.put(path, ench);
				}
			}
		}
	}

	@Override
	public void onSave(YamlConfiguration config) {
		for(BooleanOption option : options.values()) {
			option.save(this);
		}
	}

	public boolean isActive(Plugin plugin, Enchantment enchantment) {
		if(plugin == null || enchantment == null) return false;
		String path = plugin.getName() + "." + EnchantmentRegistry.getEnchantmentsMapID(enchantment);

		BooleanOption ench = options.containsKey(path) ? options.get(path) : new BooleanOption(path, true);

		if(!options.containsKey(path)) {
			options.put(path, ench);
			options.get(path).loadIfExist(this);
		}

		return ench.getValue();
	}
}
