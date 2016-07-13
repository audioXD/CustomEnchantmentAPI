package adx.audioxd.customenchantmentapi.config;


import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.RegisteredEnchantment;
import adx.audioxd.customenchantmentapi.config.option.BooleanOption;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;

public class EnchantmentsConfig extends Config {
	private LinkedHashMap<String, EnchantmentData> options = new LinkedHashMap<>();


	// Constructor
	public EnchantmentsConfig(Plugin plugin) {
		super(plugin, "Enchantments");
	}


	@Override
	public void onLoad(YamlConfiguration config) {
		for(String plugin : config.getConfigurationSection("").getValues(false).keySet()) {
			for(String enchantment : config.getConfigurationSection(plugin).getValues(false).keySet()) {
				String path = plugin + "." + enchantment;

				EnchantmentData ench = options.containsKey(path) ? options.get(path) : new EnchantmentData(path);
				ench.loadIfExist(this);

				if(!options.containsKey(path)) {
					options.put(path, ench);
				}
			}
		}
	}

	@Override
	public void onSave(YamlConfiguration config) {
		for(EnchantmentData option : options.values()) {
			option.save(this);
		}
	}

	public boolean isActive(Plugin plugin, Enchantment enchantment) {
		if(plugin == null || enchantment == null) return false;
		String path = plugin.getName() + "." + EnchantmentRegistry.getEnchantmentsMapID(enchantment);

		EnchantmentData ench = options.containsKey(path) ? options.get(path) : new EnchantmentData(path);

		if(!options.containsKey(path)) {
			options.put(path, ench);
			options.get(path).loadIfExist(this);
		}

		return ench.getIsActive().getValue();
	}

	public EnchantmentData getData(RegisteredEnchantment registeredEnchantment) {
		if(registeredEnchantment == null || registeredEnchantment.getPlugin() == null || registeredEnchantment.getEnchantment() == null)
			return null;
		String path = registeredEnchantment.getPlugin().getName() + "." + EnchantmentRegistry.getEnchantmentsMapID(registeredEnchantment.getEnchantment());

		if(!options.containsKey(path)) {
			options.put(path, new EnchantmentData(path, registeredEnchantment));
			options.get(path).loadIfExist(this);
		}
		return options.get(path);
	}

	public void update(RegisteredEnchantment registeredEnchantment, EnchantmentData data) {
		if(registeredEnchantment == null || registeredEnchantment.getPlugin() == null || registeredEnchantment.getEnchantment() == null)
			return;
		String path = registeredEnchantment.getPlugin().getName() + "." + EnchantmentRegistry.getEnchantmentsMapID(registeredEnchantment.getEnchantment());
		options.put(path, data);
	}


	public static final class EnchantmentData {
		private final BooleanOption isActive;
		public BooleanOption getIsActive() { return isActive; }

		public EnchantmentData(String pathPrefix, RegisteredEnchantment registeredEnchantment) {
			this.isActive = new BooleanOption(pathPrefix + ".active", true);
		}
		public EnchantmentData(String pathPrefix) {
			this.isActive = new BooleanOption(pathPrefix, true);
		}

		public void loadIfExist(Config config) {
			isActive.loadIfExist(config);
		}
		public void save(Config config) {
			isActive.save(config);
		}
	}
}
