package adx.audioxd.customenchantmentapi;


import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import org.bukkit.plugin.Plugin;

public class RegisteredEnchantment {
	private final Enchantment enchantment;
	private final Plugin plugin;
	private final String id;
	private boolean active;

// Constructor

	/**
	 * The Constructor.
	 *
	 * @param enchantment The Enchantment.
	 * @param plugin      The Plugin.
	 */
	public RegisteredEnchantment(Enchantment enchantment, Plugin plugin) {
		this(enchantment, plugin, false);
	}

	/**
	 * The Constructor.
	 *
	 * @param enchantment The Enchantment.
	 * @param plugin      The Plugin.
	 * @param active      If it is active
	 */
	public RegisteredEnchantment(Enchantment enchantment, Plugin plugin, boolean active) {
		if(enchantment == null || plugin == null) {
			NullPointerException e = new NullPointerException("All parameters mustn't be null");
			CustomEnchantmentAPI.getInstance().getTLogger().printException(e);
			throw e;
		}
		this.enchantment = enchantment;
		this.plugin = plugin;

		setActive(active);

		this.id = EnchantmentRegistry.getID(plugin, enchantment);
	}


	/**
	 * Gets the ID.
	 *
	 * @return The same as getID().
	 */
	public String toString() {
		return getID();
	}

	/**
	 * Gets the ID of the Registered Enchantment.
	 *
	 * @return Returns the enchantment id plugin:name
	 */
	public String getID() {
		return id;
	}

// Getters

	/**
	 * Gets the Registered Enchantment.
	 *
	 * @return The Enchantment.
	 */
	public Enchantment getEnchantment() {
		return enchantment;
	}

	/**
	 * Gets the Plugin that registered the Enchantment.
	 *
	 * @return The Plugin.
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * Gets if the Enchantment can be use / is active.
	 *
	 * @return if the Enchantment can be use / is active.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets if the Enchantment can be use / is active.
	 *
	 * @param active If the Enchantment can be use / is active.
	 */
	void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public final boolean equals(Object o) {
		if(o == this) return true;
		if(o == null || getClass() != o.getClass()) return false;
		RegisteredEnchantment other = (RegisteredEnchantment) o;
		return id.equals(other.id);
	}

}
