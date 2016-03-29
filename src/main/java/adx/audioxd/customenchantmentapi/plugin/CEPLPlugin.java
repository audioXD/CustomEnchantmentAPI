package adx.audioxd.customenchantmentapi.plugin;


import org.bukkit.plugin.java.JavaPlugin;

import adx.audioxd.customenchantmentapi.EnchantmentRegistery;

public abstract class CEPLPlugin extends JavaPlugin {

	private final TLogger logger;

	public final TLogger getPluginLogger() {
		return logger;
	}

	public CEPLPlugin() {
		logger = new TLogger(this);
	}

	@Override
	public final void onEnable() {
		logger.preEnabled(true);
		Enable();
		logger.enabled(true);
	}

	@Override
	public final void onDisable() {
		logger.preEnabled(false);
		Disable();
		EnchantmentRegistery.unregisterAll(this);
		logger.enabled(false);
	}

	public abstract void Enable();

	public abstract void Disable();

}
