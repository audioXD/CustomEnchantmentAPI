package adx.audioxd.cepi.test;


import adx.audioxd.customenchantmentapi.enums.EnchantmentPriority;
import adx.audioxd.customenchantmentapi.events.inventory.hand.EItemInHandEvent;
import org.bukkit.Material;
import org.junit.Test;

/**
 * Created by test on 26/04/2016.
 */
public class EnchnatmentTest {
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

	@Test
	public void parseText() {
		String line = tEnch.getDisplay(tEnch.getMaxLvl());
		System.out.println("===========[START]===========");
		{
			System.out.println(" - " + "Parse line '" + line + "': " + tEnch.hasCustomEnchantment(line));

			line = line + ". Some text";
			System.out.println(" - " + "Parse line '" + line + "': " + tEnch.hasCustomEnchantment(line));

			line = tEnch.getDisplay("");
			System.out.println(" - " + "Parse line '" + line + "': " + tEnch.hasCustomEnchantment(line));

			line = ". Some text";
			System.out.println(" - " + "Parse line '" + line + "': " + tEnch.hasCustomEnchantment(line));

			line = "Smite II";
			System.out.println(" - " + "Parse line '" + line + "': " + tEnch.hasCustomEnchantment(line));
		}
		System.out.println("============[END]============");
	}

	@Test
	public void priority() {
		System.out.println("HIGH compare to LOWEST: " + EnchantmentPriority.HIGH.compareTo(EnchantmentPriority.LOWEST));
	}

	@Test
	public void fireEventsPriority(){
		tEnch.fireEvent(new EItemInHandEvent(1, null, null, null), true);
	}
}
