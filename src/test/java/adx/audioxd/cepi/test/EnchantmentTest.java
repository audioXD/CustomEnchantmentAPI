package adx.audioxd.cepi.test;


import adx.audioxd.customenchantmentapi.enums.EnchantmentPriority;
import adx.audioxd.customenchantmentapi.events.bow.EBowShootEvent;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInHandEvent;
import org.bukkit.Material;
import org.junit.Test;

public class EnchantmentTest {
	// Global fields
	public static TestEnch tEnch = new TestEnch();
// End of Global Fields

	@Test
	public void getName() {
		System.out.println("Name: " + tEnch.getName());
	}

	@Test
	public void getType() {
		System.out.println("Types: ");
		for(Material m : tEnch.getType().getTypes()) {
			System.out.println(" - " + m.toString());
		}
	}

	@Test
	public void getMaxLevel() {
		System.out.println("Max Level: " + tEnch.getMaxLvl());
	}

	@Test
	public void getPriority() {
		System.out.println("Priority: " + tEnch.getPriority().toString());
	}

	@Test
	public void getDisplay() {
		System.out.println("Display: " + tEnch.getDisplay(""));

		for(int i = 0; i < (tEnch.getMaxLvl() > 3 ? 3 : tEnch.getMaxLvl()); i++) {
			System.out.println(" - " + "Display for number '" + (i + 1) + "': " + tEnch.getDisplay(i + 1));
		}
	}

	@Test
	public void getDisplayExceptionTest() {
		try {
			tEnch.getDisplay(-1);
		} catch(Exception e) {
			System.out.println("Exception!");
			return;
		}

		System.out.println("No Exception.");
	}

	@Test
	public void getToString() {
		System.out.println("To String: " + tEnch.toString());
	}


	private static final String[] toVerify = new String[] {
			tEnch.getDisplay(tEnch.getMaxLvl()),
			". Some text",
			tEnch.getDisplay(""),
			tEnch.getDisplay("bla"),
			"Smite II"
	};
	@Test
	public void parseText() {
		System.out.println("===========[START]===========");
		for(int i = 0; i < toVerify.length; i++) {
			String line = toVerify[i];
			System.out.println(" - " + "Parse line '" + line + "': " + tEnch.hasCustomEnchantment(line));
		}
		System.out.println("============[END]============");
	}

	@Test
	public void priority() {
		System.out.println("HIGH compare to LOWEST: " + EnchantmentPriority.HIGH.compareTo(EnchantmentPriority.LOWEST));
	}

	@Test
	public void fireEventsPriority() {
		EItemInHandEvent e = new EItemInHandEvent(null, null, null);
		tEnch.fireEvent(e);
	}

	@Test
	public void testIgnoreCancelled() {
		EBowShootEvent e = new EBowShootEvent(null, null, null);
		e.setCancelled(false);

		tEnch.fireEvent(e);
		System.out.println("Cancelled: " + e.isCancelled());
	}
}
