package adx.audioxd.customenchantmentapi.abst.v1_10;


import adx.audioxd.customenchantmentapi.abst.api.NSM;
import adx.audioxd.customenchantmentapi.abst.api.VersionListener;
import adx.audioxd.customenchantmentapi.listeners.CEAPIListenerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class NSMHandler implements NSM {
	public ItemStack getItemInMainHand(LivingEntity player) {
		return player.getEquipment().getItemInMainHand();
	}
	public ItemStack getItemInOffHand(LivingEntity player) {
		return player.getEquipment().getItemInOffHand();
	}

	public boolean isHandMainHAnd(PlayerInteractEvent event) {
		return EquipmentSlot.HAND.equals(event.getHand());
	}

	public VersionListener getVersionListener() { return new HSL(); }

	public static class HSL extends VersionListener {
		@EventHandler
		public void hotbarSwap(PlayerSwapHandItemsEvent event) {
			CEAPIListenerUtils.itemNotInMainHand(event.getPlayer(), event.getOffHandItem());
			CEAPIListenerUtils.itemNotInOffHand(event.getPlayer(), event.getMainHandItem());

			CEAPIListenerUtils.itemInMainHand(event.getPlayer(), event.getMainHandItem());
			CEAPIListenerUtils.itemInOffHand(event.getPlayer(), event.getOffHandItem());
		}
	}
}
