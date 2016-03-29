package adx.audioxd.customenchantmentapi.abst.api;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public interface NSU {
	public ItemStack getItemInMainHand(LivingEntity player);

	public ItemStack getItemInOffHand(LivingEntity player);

	public boolean isHandMainHAnd(PlayerInteractEvent event);

	public HotbarSwapListener getHotbarSwapListener(Method notMain, Method notOff, Method main, Method off);
}
