package adx.audioxd.customenchantmentapi.abst.v1_8;


import adx.audioxd.customenchantmentapi.abst.api.HotbarSwapListener;
import adx.audioxd.customenchantmentapi.abst.api.NSU;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NSUHandler implements NSU {
	private ItemStack NULL = new ItemStack(Material.AIR);

	public ItemStack getItemInMainHand(LivingEntity player) {
		return player.getEquipment().getItemInHand();
	}

	public ItemStack getItemInOffHand(LivingEntity player) {
		return NULL;
	}

	public boolean isHandMainHAnd(PlayerInteractEvent event) {
		return true;
	}

	public HotbarSwapListener getHotbarSwapListener(Method notMain, Method notOff, Method main, Method off) {
		return new HotbarSwapListener(notMain, notOff, main, off);
	}



}
