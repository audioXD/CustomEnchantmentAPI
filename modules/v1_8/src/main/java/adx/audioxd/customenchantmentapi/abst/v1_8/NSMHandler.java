package adx.audioxd.customenchantmentapi.abst.v1_8;


import adx.audioxd.customenchantmentapi.abst.api.NSM;
import adx.audioxd.customenchantmentapi.abst.api.VersionListener;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class NSMHandler implements NSM {
	public ItemStack getItemInMainHand(LivingEntity player) {
		return player.getEquipment().getItemInHand();
	}

	private static ItemStack NULL = new ItemStack(Material.AIR);
	public ItemStack getItemInOffHand(LivingEntity player) {
		return NULL;
	}

	public boolean isHandMainHAnd(PlayerInteractEvent event) {
		return true;
	}

	public VersionListener getVersionListener() {
		return new VersionListener();
	}
}
