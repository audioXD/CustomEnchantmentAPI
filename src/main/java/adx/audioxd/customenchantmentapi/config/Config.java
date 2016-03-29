package adx.audioxd.customenchantmentapi.config;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class Config {
	protected final File configFile;

	public final File getFile() {
		return configFile;
	}

	protected final File localFile;

	protected YamlConfiguration config;

	public YamlConfiguration getConfig() {
		return config;
	}

	protected final Plugin plugin;

	public Config(Plugin plugin, String file) {
		this.localFile = new File(file + ".yml");
		this.configFile = new File(plugin.getDataFolder(), localFile.getPath());
		this.plugin = plugin;
	}
	
	public void createFileIfDoesNotExist(){
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			copy(plugin.getResource(localFile.getName()), configFile);
		}
	}

	public final void load() {
		createFileIfDoesNotExist();
		config = YamlConfiguration.loadConfiguration(configFile);
		onLoad(config);
	}

	public final void save() {
		onSave(config);
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void onLoad(YamlConfiguration config);

	public abstract void onSave(YamlConfiguration config);

	private void copy(InputStream in, File file) {
		if (in == null || file == null) return;
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
