package adx.audioxd.customenchantmentapi.plugin;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.logging.Level;

public class TLogger {
	private final Plugin plugin;
	private boolean DEBUG = false;
	private boolean PUBLIC = true;

	// Constructor
	public TLogger(Plugin plugin) {
		this.plugin = plugin;
	}

	public void severe(String message) {
		log(Level.SEVERE, message);
	}

	public void log(Level ll, String message) {
		CustomEnchantmentAPI.getCeapiLogger().log(ll, "<" + plugin.getName() + ">: " + message);
		if(PUBLIC) plugin.getLogger().log(ll, message);
	}

	public void debug(String message) {
		if(DEBUG) log(Level.INFO, "[DEBUG] " + message);
	}

	public void warning(String message) {
		log(Level.WARNING, message);
	}

	public void fine(String message) {
		log(Level.FINE, message);
	}

	public void finer(String message) {
		log(Level.FINER, message);
	}

	public void finest(String message) {
		log(Level.FINEST, message);
	}

	public void preEnabled(boolean enabled) {
		if(enabled) {
			PluginDescriptionFile p = plugin.getDescription();
			info(p.getName() + " Version: " + p.getVersion() + " Is being Enabled!");
		} else {
			PluginDescriptionFile p = plugin.getDescription();
			info(p.getName() + " Version: " + p.getVersion() + " Is being Disabled!");
		}
	}

	public void info(String message) {
		log(Level.INFO, message);
	}

	public void enabled(boolean enabled) {
		if(enabled) {
			PluginDescriptionFile p = plugin.getDescription();
			info(p.getName() + " Version: " + p.getVersion() + " Has ben Enabled!");
		} else {
			PluginDescriptionFile p = plugin.getDescription();
			info(p.getName() + " Version: " + p.getVersion() + " Has ben Disabled!");
		}
	}

	// Getters
	public boolean isDebug() {
		return DEBUG;
	}

	public void setDebug(boolean debug) {
		DEBUG = debug;
	}

	public boolean isPublic() {
		return PUBLIC;
	}

	public void setPublic(boolean isPublic) {
		PUBLIC = isPublic;
	}
}
