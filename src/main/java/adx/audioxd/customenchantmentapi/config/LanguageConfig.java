package adx.audioxd.customenchantmentapi.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import adx.audioxd.customenchantmentapi.config.option.StringOption;

public class LanguageConfig extends Config {
	public final StringOption RELOAD_CONFIG;

	public final StringOption NO_PERMISSION;
	public final StringOption NOT_ENOUGHT_ARGUMENTS;
	public final StringOption UNKNOWN_COMMAND;
	public final StringOption UNKNOWN_ENCHANTMENT;
	public final StringOption NOT_PLAYER;
	public final StringOption LEVEL_LESS_THAN_ONE;

	public final StringOption ENCHANT_NO_ACCES_TO_ENCHANTMENT;
	public final StringOption ENCHANT_SUCCES;
	public final StringOption ENCHANT_ERROR;

	public final StringOption UNENCHANT_NO_ACCES_TO_ENCHANTMENT;
	public final StringOption UNENCHANT_SUCCES;
	public final StringOption UNENCHANT_ERROR;
	

	public LanguageConfig(Plugin plugin, String file) {
		super(plugin, file);
		RELOAD_CONFIG = new StringOption("messages.reloadConfig", "§2Reloaded config files.");

		NO_PERMISSION = new StringOption("error.errorYouDoNotHavePermission",
				"§cYou don't have acces to use that command.");
		NOT_ENOUGHT_ARGUMENTS = new StringOption("error.errorNotEnoughArguments", "§cNot enought arguments.");
		UNKNOWN_COMMAND = new StringOption("error.unknownCommand", "§cUnknown command!");
		UNKNOWN_ENCHANTMENT = new StringOption("error.unknownCommand", "§cUnknown Enchantment!");
		NOT_PLAYER = new StringOption("error.notPlayer", "§cThis command must be executed by a player!");

		ENCHANT_NO_ACCES_TO_ENCHANTMENT = new StringOption("enchant.noAccesToEnchantment",
				"§cYou don't have acces to enchant that Enchantment");
		ENCHANT_SUCCES = new StringOption("enchant.succes", "§2Enchanted item in hand with %s");
		ENCHANT_ERROR = new StringOption("enchant.error", "§cDidn't enchant item with %s");
		LEVEL_LESS_THAN_ONE = new StringOption("enchant.errorLessThanOne", "§5The level must be more or equal to 1.");

		UNENCHANT_NO_ACCES_TO_ENCHANTMENT = new StringOption("unenchant.noAccesToEnchantment",
				"§cYou don't have acces to unenchant that Enchantment");
		UNENCHANT_SUCCES = new StringOption("unenchant.succes", "§2Un-enchanted item in hand with %s");
		UNENCHANT_ERROR = new StringOption("unenchant.error", "§cDidn't un-enchant item with %s");

		load();
	}

	@Override
	public void onLoad(YamlConfiguration config) {
		RELOAD_CONFIG.loadIfExist(this);

		NO_PERMISSION.loadIfExist(this);
		NOT_ENOUGHT_ARGUMENTS.loadIfExist(this);
		UNKNOWN_COMMAND.loadIfExist(this);
		UNKNOWN_ENCHANTMENT.loadIfExist(this);
		NOT_PLAYER.loadIfExist(this);
		LEVEL_LESS_THAN_ONE.loadIfExist(this);

		ENCHANT_NO_ACCES_TO_ENCHANTMENT.loadIfExist(this);
		ENCHANT_SUCCES.loadIfExist(this);
		ENCHANT_ERROR.loadIfExist(this);

		UNENCHANT_NO_ACCES_TO_ENCHANTMENT.loadIfExist(this);
		UNENCHANT_SUCCES.loadIfExist(this);
		UNENCHANT_ERROR.loadIfExist(this);
	}

	public void onSave(YamlConfiguration config) {
	}

}
