package adx.audioxd.customenchantmentapi.abst.api;


import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VersionListenr implements Listener {
	private final Method notMain, notOff, main, off;

	public VersionListenr(Method notMain, Method notOff, Method main, Method off) {
		this.notMain = notMain;
		this.notOff = notOff;
		this.main = main;
		this.off = off;
	}

	public void itemNotInMainHand(LivingEntity owner, ItemStack item) {
		invoke(notMain, owner, item);
	}

	public void itemInMainHand(LivingEntity owner, ItemStack item) {
		invoke(main, owner, item);
	}

	public void itemNotInOffHand(LivingEntity owner, ItemStack item) {
		invoke(notOff, owner, item);
	}

	public void itemInOffHand(LivingEntity owner, ItemStack item) {
		invoke(off, owner, item);
	}

	private void invoke(Method method, LivingEntity owner, ItemStack item) {
		try {
			method.invoke(null, owner, item);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
