package adx.audioxd.customenchantmentapi.config;


import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import adx.audioxd.customenchantmentapi.config.option.StringOption;

public class DefaultConfig extends Config {

	public final StringOption MESSAGE_LOCALIZATION_FILE;

	public DefaultConfig(Plugin plugin) {
		super(plugin, "config");

		MESSAGE_LOCALIZATION_FILE = new StringOption("messageLocalizationFile", "en-US");
	}

	@Override
	public void onLoad(YamlConfiguration config) {
		MESSAGE_LOCALIZATION_FILE.loadIfExist(this);

	}

	@Override
	public void onSave(YamlConfiguration config) {

	}

}
