package adx.audioxd.customenchantmentapi;


import adx.audioxd.customenchantmentapi.abst.api.NSM;
import adx.audioxd.customenchantmentapi.commands.PluginTabComplete;
import adx.audioxd.customenchantmentapi.config.DefaultConfig;
import adx.audioxd.customenchantmentapi.config.LanguageConfig;
import adx.audioxd.customenchantmentapi.listeners.*;
import adx.audioxd.customenchantmentapi.plugin.TLogger;
import adx.audioxd.customenchantmentapi.utils.GameLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class CustomEnchantmentAPI extends JavaPlugin {
	// Global fields
	private static final GameLogger ceapiLogger = new GameLogger();
	private static CustomEnchantmentAPI instance;
	// End of Global Fields
	private final TLogger logger;
	private final DefaultConfig dc;
	private final String version;
	private LanguageConfig lc;
	private NSM nsm;

	// Constructor
	public CustomEnchantmentAPI() {
		String packageName = this.getServer().getClass().getPackage().getName();
		String v1 = packageName.substring(packageName.lastIndexOf('.') + 1);
		version = v1.split("_")[0] + "_" + v1.split("_")[1];

		instance = this;
		logger = new TLogger(this);
		ceapiLogger.getLogger().setParent(this.getLogger());
		ceapiLogger.getLogger().setUseParentHandlers(false);
		ceapiLogger.createDefaultLogFiles(this.getDataFolder());
		{
			dc = new DefaultConfig(this);
			reloadConfigs();
		}
	}

	/**
	 * This method creates(If they don't exist) and reloads the config Files.
	 */
	public void reloadConfigs() {
		(new DefaultConfig(this)).createFileIfDoesNotExist();
		(new LanguageConfig(this, "/locale/en-US")).createFileIfDoesNotExist();
		(new LanguageConfig(this, "/locale/Template-en-US")).createFileIfDoesNotExist();

		dc.load();
		lc = new LanguageConfig(this, "locale/" + dc.MESSAGE_LOCALIZATION_FILE.getValue());
	}

	@Override
	public void onEnable() {
		instance = this;
		ceapiLogger.createDefaultLogFiles(this.getDataFolder());
		logger.preEnabled(true);
		{
			logger.info("Bukkit version: " + version);
			try {
				final Class<?> clazz = Class.forName("adx.audioxd.customenchantmentapi.abst." + version + ".NSMHandler");
				if(NSM.class.isAssignableFrom(clazz)) { // Make sure it actually implements NMS
					this.nsm = (NSM) clazz.getConstructor().newInstance(); // Set our handler
				}
			} catch(final Exception e) {
				logger.info(e.getMessage());
				logger.severe("Could not find support for Spigot " + version + ".");
				this.setEnabled(false);
				onDisable();
				return;
			}

			reloadConfigs();

			Bukkit.getPluginManager().registerEvents(new onArmorListener(this), this);
			Bukkit.getPluginManager().registerEvents(new onItemInHandChange(this), this);
			Bukkit.getPluginManager().registerEvents(new onBlockBreakEvent(this), this);
			Bukkit.getPluginManager().registerEvents(new onBlockPlaceEvent(this), this);
			Bukkit.getPluginManager().registerEvents(new onBowShotEvent(this), this);
			Bukkit.getPluginManager().registerEvents(new onEntityDamage(this), this);
			Bukkit.getPluginManager().registerEvents(new onEntityDamagedByEntity(this), this);
			Bukkit.getPluginManager().registerEvents(new onInteract(this), this);
			try {
				Method notMain = CEPLListener.class.getMethod("itemNotInMainHand", LivingEntity.class, ItemStack.class);
				Method notOff = CEPLListener.class.getMethod("itemNotInOffHand", LivingEntity.class, ItemStack.class);
				Method main = CEPLListener.class.getMethod("itemInMainHand", LivingEntity.class, ItemStack.class);
				Method off = CEPLListener.class.getMethod("itemInOffHand", LivingEntity.class, ItemStack.class);
				if(notMain == null || notOff == null || main == null || off == null)
					return;
				Bukkit.getPluginManager().registerEvents(nsm.getVersionListener(notMain, notOff, main, off), this);
			} catch(NoSuchMethodException e) {
				e.printStackTrace();
			}

			TabExecutor te = new PluginTabComplete();
			getCommand("ceapi").setTabCompleter(te);
			getCommand("ceapi").setExecutor(te);

			getCommand(this.getDescription().getName()).setTabCompleter(te);
			getCommand(this.getDescription().getName()).setExecutor(te);
		}
		logger.enabled(true);
	}

	@Override
	public void onDisable() {
		logger.preEnabled(false);
		{
			EnchantmentRegistry.reset();
		}
		logger.enabled(false);
		ceapiLogger.closeActiveLogFiles();
		instance = null;
	}

	// Getters

	/**
	 * This method returns the currently running instance of Plugin
	 *
	 * @return The plugin
	 */
	public static CustomEnchantmentAPI getInstance() {
		return instance;
	}

	/**
	 * Returns the logger for all Custom Enchantment API Plugins
	 *
	 * @return the Custom Enchantment API Logger
	 */
	public static GameLogger getCEAPILogger() {
		return ceapiLogger;
	}

	/**
	 * Returns the logger For the CustomEnchantmentAPI Plugin.
	 *
	 * @return The logger
	 */
	public TLogger getTLogger() {
		return logger;
	}

	/**
	 * Returns the config.yml file and its options.
	 *
	 * @return The config.yml file.
	 */
	public DefaultConfig getDefaultConfig() {
		return dc;
	}

	/**
	 * Returns the currently defined LanguageConfig in the DefaultConfig.
	 *
	 * @return The current LanguageConfig.
	 */
	public LanguageConfig getLanguageConfig() {
		return lc;
	}

	/**
	 * Returns the NSU used dynamic-ly assigned by the current Bukkit version.
	 *
	 * @return The dynamic NSU version
	 */
	public NSM getNSM() {
		return nsm;
	}
}
