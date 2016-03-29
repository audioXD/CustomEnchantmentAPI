package adx.audioxd.customenchantmentapi.abst.v1_9;


import adx.audioxd.customenchantmentapi.abst.api.HotbarSwapListener;
import adx.audioxd.customenchantmentapi.abst.api.NSU;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NSUHandler implements NSU {
	public ItemStack getItemInMainHand(LivingEntity player) {
		return player.getEquipment().getItemInMainHand();
	}

	public ItemStack getItemInOffHand(LivingEntity player) {
		return player.getEquipment().getItemInOffHand();
	}

	public boolean isHandMainHAnd(PlayerInteractEvent event) {
		return EquipmentSlot.HAND.equals(event.getHand());
	}

	public HotbarSwapListener getHotbarSwapListener(Method notMain, Method notOff, Method main, Method off) {
		return new HSL(notMain, notOff, main, off);
	}

	public static class HSL extends HotbarSwapListener{
		HSL(Method notMain, Method notOff, Method main, Method off) {
			super(notMain, notOff, main, off);
		}

		@EventHandler
		public void hotbarSwap(PlayerSwapHandItemsEvent event){
			this.itemNotInMainHand(event.getPlayer(), event.getOffHandItem());
			this.itemNotInOffHand(event.getPlayer(), event.getMainHandItem());

			this.itemInMainHand(event.getPlayer(), event.getMainHandItem());
			this.itemInOffHand(event.getPlayer(), event.getOffHandItem());
		}
	}
}
