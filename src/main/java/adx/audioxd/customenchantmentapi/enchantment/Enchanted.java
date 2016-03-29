package adx.audioxd.customenchantmentapi.enchantment;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.forhelp.Lvl;

public class Enchanted implements Lvl {
	private final int lvl;

	public int getLvl() {
		return lvl;
	}

	private final Enchantment enchantment;

	public Enchantment getEncnantment() {
		return enchantment;
	}

	public Enchanted(int lvl, Enchantment enchantment) {
		this.lvl = lvl;
		this.enchantment = enchantment;
	}

	public void fireEvent(EnchantmentEvent event) {
		if (enchantment != null) enchantment.fireEvent(event);
	}
}
