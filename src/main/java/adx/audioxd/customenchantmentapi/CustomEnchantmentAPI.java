package adx.audioxd.customenchantmentapi;


import adx.audioxd.customenchantmentapi.abst.api.NSM;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.ceapi.Ceapi;
import adx.audioxd.customenchantmentapi.config.DefaultConfig;
import adx.audioxd.customenchantmentapi.config.EnchantmentsConfig;
import adx.audioxd.customenchantmentapi.config.LanguageConfig;
import adx.audioxd.customenchantmentapi.listeners.*;
import adx.audioxd.customenchantmentapi.plugin.TLogger;
import adx.audioxd.customenchantmentapi.utils.GameLogger;
import adx.audioxd.customenchantmentapi.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class CustomEnchantmentAPI extends JavaPlugin {
	// Global fields
	private static final GameLogger ceapiLogger = new GameLogger();
	/**
	 * Returns the logger for all Custom Enchantment API Plugins
	 *
	 * @return the Custom Enchantment API Logger
	 */
	public static GameLogger getCEAPILogger() { return ceapiLogger; }

	private static CustomEnchantmentAPI instance;
	/**
	 * This method returns the currently running instance of Plugin
	 *
	 * @return The plugin
	 */
	public static CustomEnchantmentAPI getInstance() { return instance; }

	// End of Global Fields
	private final TLogger logger;
	/**
	 * Returns the logger For the CustomEnchantmentAPI Plugin.
	 *
	 * @return The logger
	 */
	public TLogger getTLogger() { return logger; }

	private final DefaultConfig dc;
	/**
	 * Returns the config.yml file and its options.
	 *
	 * @return The config.yml file.
	 */
	public DefaultConfig getDefaultConfig() { return dc; }

	private final EnchantmentsConfig ec;
	/**
	 * Returns the Enchantments.yml file.
	 *
	 * @return The Enchantments.yml file.
	 */
	public EnchantmentsConfig getEnchantmentsConfig() { return ec; }

	private LanguageConfig lc;
	/**
	 * Returns the currently defined LanguageConfig in the DefaultConfig.
	 *
	 * @return The current LanguageConfig.
	 */
	public LanguageConfig getLanguageConfig() { return lc; }

	private final String version;
	private NSM nsm;
	/**
	 * Returns the NSU used dynamic-ly assigned by the current Bukkit version.
	 *
	 * @return The dynamic NSU version
	 */
	public NSM getNSM() { return nsm; }

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
			ec = new EnchantmentsConfig(this);
			reloadConfigs();
		}
	}

	private static final String[] languageConfigs = new String[]{
			"en-US",
			"Template-en-US"
	};
	/**
	 * This method creates(If they don't exist) and reloads the config Files.
	 */
	public void reloadConfigs() {
		dc.createFileIfDoesNotExist();
		ec.createFileIfDoesNotExist();

		for(String languageConfig: languageConfigs){
			File newFile = new File(this.getDataFolder(), "/locale/" + languageConfig + ".yml");
			if(newFile.exists()) continue;

			logger.info("File: 'locale/" + newFile.getName() + "' doesn't exist, creating...");
			InputStream in = null;
			FileOutputStream out = null;
			try {
				in = this.getResource(languageConfig + ".yml");
				out = new FileOutputStream(newFile);
				int read;
				byte[] bytes = new byte[1024];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				logger.info("Created File: 'locale/" + newFile.getName() + "'");
			} catch(Exception e){
				logger.warning("Couldn't create File: 'locale/" + newFile.getName() + "'");
			} finally {
				try{
					if(in != null)
						in.close();
					if(out != null)
						out.close();
				} catch(IOException ignored) { }
			}
		}
		dc.load();
		ec.load();
		lc = new LanguageConfig(this, "locale/" + dc.MESSAGE_LOCALIZATION_FILE.getValue());

		EnchantmentRegistry.rebuildEnchantmentsArray();
	}

	@Override public void onEnable() {
		instance = this;
		ceapiLogger.createDefaultLogFiles(this.getDataFolder());
		logger.preEnabled(true);
		{
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

			CEAPICommand.registerCommand(this, new Ceapi());

			if(dc.CHECK_FOR_UPDATES.getValue()) {
				UpdateChecker uc = new UpdateChecker("http://dev.bukkit.org/bukkit-plugins/customeenchantmentapi/files.rss");
				if(uc.isUpdateNeeded(this)) {
					logger.info(lc.NEW_VERSION_AVAILABLE.format(uc.getLatestVersion(), uc.getDownloadLink()));
				} else {
					logger.info(lc.NO_VERSION_AVAILABLE.format());
				}
			}
		}
		logger.enabled(true);
	}
	@Override public void onDisable() {
		logger.preEnabled(false);
		{
			EnchantmentRegistry.reset();
		}
		logger.enabled(false);
		ceapiLogger.closeActiveLogFiles();
		instance = null;
	}
}
