package adx.audioxd.customenchantmentapi.enchantment.event.extra;


import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Lvl;

public abstract class EnchantmentEvent implements Lvl, adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent {

	private final int lvl;

	// Constructor
	public EnchantmentEvent(int lvl) {
		this.lvl = lvl;
	}

	// Getters
	final public int getLvl() {
		return lvl;
	}
}
