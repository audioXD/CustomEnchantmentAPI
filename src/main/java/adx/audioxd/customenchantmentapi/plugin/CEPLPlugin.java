package adx.audioxd.customenchantmentapi.plugin;


import adx.audioxd.customenchantmentapi.EnchantmentRegistery;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CEPLPlugin extends JavaPlugin {
	private final TLogger logger;

	// Constructor
	public CEPLPlugin() {
		logger = new TLogger(this);
	}

	@Override
	public final void onEnable() {
		logger.preEnabled(true);
		Enable();
		logger.enabled(true);
	}

	public abstract void Enable();

	@Override
	public final void onDisable() {
		logger.preEnabled(false);
		Disable();
		EnchantmentRegistery.unregisterAll(this);
		logger.enabled(false);
	}

	public abstract void Disable();

	// Getters
	public final TLogger getPluginLogger() {
		return logger;
	}

}
