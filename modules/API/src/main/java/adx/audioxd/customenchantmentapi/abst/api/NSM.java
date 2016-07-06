package adx.audioxd.customenchantmentapi.abst.api;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public interface NSM {
	ItemStack getItemInMainHand(LivingEntity player);
	ItemStack getItemInOffHand(LivingEntity player);

	boolean isHandMainHAnd(PlayerInteractEvent event);

	VersionListener getVersionListener();
}
