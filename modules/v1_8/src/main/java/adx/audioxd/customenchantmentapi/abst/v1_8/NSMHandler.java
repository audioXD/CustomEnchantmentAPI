package adx.audioxd.customenchantmentapi.abst.v1_8;


import adx.audioxd.customenchantmentapi.abst.api.NSM;
import adx.audioxd.customenchantmentapi.abst.api.VersionListener;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NSMHandler implements NSM {
	// Global fields
	private static ItemStack NULL = new ItemStack(Material.AIR);
// End of Global Fields

	public ItemStack getItemInMainHand(LivingEntity player) {
		return player.getEquipment().getItemInHand();
	}

	public ItemStack getItemInOffHand(LivingEntity player) {
		return NULL;
	}

	public boolean isHandMainHAnd(PlayerInteractEvent event) {
		return true;
	}

	public VersionListener getVersionListener(Method notMain, Method notOff, Method main, Method off) {
		return new VersionListener(notMain, notOff, main, off);
	}

	// Getters
	public ItemStack getNull() {
		return NULL;
	}

}
