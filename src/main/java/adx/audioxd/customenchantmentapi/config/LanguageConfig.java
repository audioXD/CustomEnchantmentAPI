package adx.audioxd.customenchantmentapi.config;


import adx.audioxd.customenchantmentapi.config.option.StringOption;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class LanguageConfig extends Config {
	public final StringOption RELOAD_CONFIG;

	public final StringOption NO_PERMISSION;
	public final StringOption NOT_ENOUGH_ARGUMENTS;
	public final StringOption UNKNOWN_COMMAND;
	public final StringOption UNKNOWN_ENCHANTMENT;
	public final StringOption NOT_PLAYER;
	public final StringOption LEVEL_LESS_THAN_ONE;

	public final StringOption ENCHANT_NO_ACCESS_TO_ENCHANTMENT;
	public final StringOption ENCHANT_SUCCESS;
	public final StringOption ENCHANT_ERROR;

	public final StringOption UNENCHANT_NO_ACCESS_TO_ENCHANTMENT;
	public final StringOption UNENCHANT_SUCCESS;
	public final StringOption UNENCHANT_ERROR;

	public final StringOption NEW_VERSION_AVAILABLE;
	public final StringOption NO_VERSION_AVAILABLE;


	// Constructor
	public LanguageConfig(Plugin plugin, String file) {
		super(plugin, file);
		RELOAD_CONFIG = new StringOption("messages.reloadConfig", "&2Reloaded config files.");

		NO_PERMISSION = new StringOption(
				"error.errorYouDoNotHavePermission",
				"&cYou don't have access to use that command."
		);
		NOT_ENOUGH_ARGUMENTS = new StringOption("error.errorNotEnoughArguments", "&cNot enough arguments.");
		UNKNOWN_COMMAND = new StringOption("error.unknownCommand", "&cUnknown command!");
		UNKNOWN_ENCHANTMENT = new StringOption("error.unknownCommand", "&cUnknown Enchantment!");
		NOT_PLAYER = new StringOption("error.notPlayer", "&cThis command must be executed by a player!");

		ENCHANT_NO_ACCESS_TO_ENCHANTMENT = new StringOption(
				"enchant.noAccessToEnchantment",
				"&cYou don't have access to enchant that Enchantment"
		);
		ENCHANT_SUCCESS = new StringOption("enchant.success", "&2Enchanted item in hand with %s");
		ENCHANT_ERROR = new StringOption("enchant.error", "&cDidn't enchant item with %s");
		LEVEL_LESS_THAN_ONE = new StringOption("enchant.errorLessThanOne", "&5The level must be more or equal to 1.");

		UNENCHANT_NO_ACCESS_TO_ENCHANTMENT = new StringOption(
				"unenchant.noAccessToEnchantment",
				"&cYou don't have access to unenchant that Enchantment"
		);
		UNENCHANT_SUCCESS = new StringOption("unenchant.success", "&2Un-enchanted item in hand with %s");
		UNENCHANT_ERROR = new StringOption("unenchant.error", "&cDidn't un-enchant item with %s");

		NEW_VERSION_AVAILABLE = new StringOption("info.newVersionAvailable", "A new version is available version: %1$s, download link: %2$s");
		NO_VERSION_AVAILABLE = new StringOption("info.noVersionAvailable", "No new version are available.");
		load();
	}

	@Override
	public void onLoad(YamlConfiguration config) {
		RELOAD_CONFIG.loadIfExist(this);

		NO_PERMISSION.loadIfExist(this);
		NOT_ENOUGH_ARGUMENTS.loadIfExist(this);
		UNKNOWN_COMMAND.loadIfExist(this);
		UNKNOWN_ENCHANTMENT.loadIfExist(this);
		NOT_PLAYER.loadIfExist(this);
		LEVEL_LESS_THAN_ONE.loadIfExist(this);

		ENCHANT_NO_ACCESS_TO_ENCHANTMENT.loadIfExist(this);
		ENCHANT_SUCCESS.loadIfExist(this);
		ENCHANT_ERROR.loadIfExist(this);

		UNENCHANT_NO_ACCESS_TO_ENCHANTMENT.loadIfExist(this);
		UNENCHANT_SUCCESS.loadIfExist(this);
		UNENCHANT_ERROR.loadIfExist(this);

		NEW_VERSION_AVAILABLE.loadIfExist(this);
		NO_VERSION_AVAILABLE.loadIfExist(this);
	}

	public void onSave(YamlConfiguration config) {}

}
