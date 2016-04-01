package adx.audioxd.customenchantmentapi.config;


import adx.audioxd.customenchantmentapi.config.option.StringOption;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class DefaultConfig extends Config {

	// Class base fields
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
