package adx.audioxd.customenchantmentapi.utils;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectUtils {

	// Methods
	public static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch(Exception e) {
			CustomEnchantmentAPI.getCeapiLogger().printException(e);
		}
	}

	public static Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch(ClassNotFoundException e) {
			CustomEnchantmentAPI.getCeapiLogger().printException(e);
			return null;
		}
	}

	public static Class<?> getBukkitNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
		} catch(ClassNotFoundException e) {
			CustomEnchantmentAPI.getCeapiLogger().printException(e);
			return null;
		}
	}
}
