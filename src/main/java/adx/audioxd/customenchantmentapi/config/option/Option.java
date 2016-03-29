package adx.audioxd.customenchantmentapi.config.option;


import adx.audioxd.customenchantmentapi.config.Config;

public class Option {
	private final String path;
	private Object value;

	public String getPath() {
		return path;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public Option(String path, Object value) {
		this.path = path;
		this.value = value;
	}

	public final void loadIfExist(Config config) {
		loadIfExist(config, this);
	}

	public static final void loadIfExist(Config config, Option option) {
		if (config.getConfig().isSet(option.getPath())) {
			option.setValue(config.getConfig().get(option.getPath()));
		} else {
			save(config, option);
			config.save();
		}
	}

	public final void save(Config config) {
		save(config, this);
	}

	public static final void save(Config config, Option option) {
		config.getConfig().set(option.getPath(), option.getValue());
	}
}
