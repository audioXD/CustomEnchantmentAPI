package adx.audioxd.customenchantmentapi.abst.api;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * Created by test on 29/03/2016.
 */
public interface NSM {
	public ItemStack getItemInMainHand(LivingEntity player);

	public ItemStack getItemInOffHand(LivingEntity player);

	public boolean isHandMainHAnd(PlayerInteractEvent event);

	public VersionListenr getVersionListener(Method notMain, Method notOff, Method main, Method off);
}
