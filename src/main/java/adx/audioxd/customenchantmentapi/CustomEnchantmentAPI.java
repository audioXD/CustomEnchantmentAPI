package adx.audioxd.customenchantmentapi;


import adx.audioxd.customenchantmentapi.abst.api.NSU;
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
	private static CustomEnchantmentAPI instance;

	public static CustomEnchantmentAPI getInstace() {
		return instance;
	}

	private static final GameLogger ceapiLogger = new GameLogger();

	public static GameLogger getCeapiLogger() {
		return ceapiLogger;
	}

	private final TLogger logger;

	public TLogger getTLogger() {
		return logger;
	}

	private final DefaultConfig dc;

	public DefaultConfig getDefaultConfig() {
		return dc;
	}

	private LanguageConfig lc;

	public LanguageConfig getLanguageConfig() {
		return lc;
	}

	private NSU nsu;

	public NSU getNSU() {
		return nsu;
	}

	public CustomEnchantmentAPI() {
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

	@Override public void onEnable() {
		instance = this;
		ceapiLogger.createDefaultLogFiles(this.getDataFolder());
		logger.preEnabled(true);
		{
			String packageName = this.getServer().getClass().getPackage().getName();
			String version = packageName.substring(packageName.lastIndexOf('.') + 1);
			version = version.split("_")[0] + "_" + version.split("_")[1];
			try {
				final Class<?> clazz = Class
						.forName("adx.audioxd.customenchantmentapi.abst." + version + ".NSUHandler");
				if (NSU.class.isAssignableFrom(clazz)) { // Make sure it actually implements NMS
					this.nsu = (NSU) clazz.getConstructor().newInstance(); // Set our handler
				}
			} catch (final Exception e) {
				e.printStackTrace();
				logger.severe("Could not find support for Spigot " + version + ".");
				this.setEnabled(false);
				return;
			}
			logger.info("Bukkit version: " + version);

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
				Bukkit.getPluginManager().registerEvents(nsu.getHotbarSwapListener(notMain, notOff, main, off), this);
			} catch (NoSuchMethodException e) {
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

	@Override public void onDisable() {
		logger.preEnabled(false);
		{
			EnchantmentRegistery.reset();
		}
		logger.enabled(false);
		ceapiLogger.closeActiveLogFiles();
		instance = null;
	}

	public void reloadConfigs() {
		(new DefaultConfig(this)).createFileIfDoesNotExist();
		(new LanguageConfig(this, "/locale/en-US")).createFileIfDoesNotExist();
		(new LanguageConfig(this, "/locale/Template-en-US")).createFileIfDoesNotExist();

		dc.load();
		lc = new LanguageConfig(this, "locale/" + dc.MESSAGE_LOCALIZATION_FILE.getValue());
	}
}
