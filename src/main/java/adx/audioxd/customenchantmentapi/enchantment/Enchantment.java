package adx.audioxd.customenchantmentapi.enchantment;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enums.EnchantmentPriority;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.utils.RomanNumeral;
import org.bukkit.ChatColor;

public abstract class Enchantment implements Comparable<Enchantment> {
	protected final String name;
	protected final ItemType type;
	protected final int maxLvl;
	protected final EnchantmentPriority priority;
	private final EventBus eb;

// Constructor
	/**
	 * @param name   The name of the enchantment
	 * @param type   The ItemType that the Enchantment can be enchanted on.
	 * @param maxLvl The max level that the Enchantment can be.
	 */
	public Enchantment(String name, ItemType type, int maxLvl) {
		this(name, type, maxLvl, EnchantmentPriority.NORMAL);
	}

	/**
	 * @param name     The name of the enchantment
	 * @param type     The ItemType that the Enchantment can be enchanted on.
	 * @param maxLvl   The max level that the Enchantment can be.
	 * @param priority The priority that the Enchantment has.
	 */
	public Enchantment(String name, ItemType type, int maxLvl, EnchantmentPriority priority) {
		this.name = name;
		this.type = type;
		this.maxLvl = maxLvl;
		this.priority = priority;
		eb = new EventBus(this);
	}

	/**
	 * Gets the display name that the enchantment will have when enchanted.
	 *
	 * @param lvl The level that the display name will have.(If 0 or less it
	 *            throws a Exception)
	 * @return The display name
	 */
	public final String getDisplay(int lvl) {
		return getDisplay(RomanNumeral.getRomanFromInt(lvl));
	}

	/**
	 * Gets the display name that the enchantment will have when enchanted.
	 *
	 * @param romanNumeral The roman numeral.
	 * @return The display name.
	 */
	public String getDisplay(String romanNumeral) {
		return name + " " + romanNumeral;
	}

	/**
	 * Returns a boolean if the String is equal to the Enchantment display name.
	 *
	 * @param line The input String
	 * @return If the String is equal to the Enchantment display name.
	 */
	public final boolean hasCustomEnchantment(String line) {
		if(line == null) return false;
		if(line.equalsIgnoreCase("")) return false;

		return (line.startsWith(this.getDisplay(""))
				&& (ChatColor.stripColor(this.getDisplay("")).split("\\s+").length + 1) == ChatColor.stripColor(line)
				.split("\\s+").length);
	}

	@Override
	public final int compareTo(Enchantment other) {
		return other.priority.compareTo(priority);
	}

	@Override
	public final boolean equals(Object o) {
		if(o == this) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Enchantment other = (Enchantment) o;
		return other.getDisplay("").equals(getDisplay(""));
	}

	@Override
	public final String toString() {
		return getDisplay("");
	}

	@Override
	public final int hashCode() {
		return getDisplay("").hashCode();
	}


	/**
	 * Fires the event.
	 *
	 * @param event The event
	 */
	public final void fireEvent(EnchantmentEvent event) {
		eb.fireEvent(event);
	}

// Getters
	/**
	 * Gets the Enchantment name
	 *
	 * @return The Enchantment name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the items on which the Enchantment can be enchanted on.
	 *
	 * @return A TemType object.
	 */
	public ItemType getType() {
		return type;
	}

	/**
	 * Gets the max level of the Enchantment.
	 *
	 * @return The max level.
	 */
	public int getMaxLvl() {
		return maxLvl;
	}

	/**
	 * Gets the priority of the Enchantment.
	 *
	 * @return The Enchantments priority.
	 */
	public EnchantmentPriority getPriority() {
		return priority;
	}
}
