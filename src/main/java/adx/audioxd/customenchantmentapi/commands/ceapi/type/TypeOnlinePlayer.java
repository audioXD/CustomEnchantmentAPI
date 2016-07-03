package adx.audioxd.customenchantmentapi.commands.ceapi.type;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.commands.exceptions.TypeException;
import adx.audioxd.customenchantmentapi.commands.type.TypeAbstract;
import adx.audioxd.customenchantmentapi.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeOnlinePlayer extends TypeAbstract<Player> {
	@Override
	public String getInvelidErrorMessage(String arg) {
		return CustomEnchantmentAPI.getInstance().getLanguageConfig().ONLINE_PLAYER_NOT_FOUND.format(arg);
	}

	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> onlinePlayers = new ArrayList<>();
		for(Player player: Bukkit.getServer().getOnlinePlayers()){
			if(!player.isOnline() || !player.isValid()) continue;
			onlinePlayers.add(player.getName());
		}
		return Text.getStartsWithIgnoreCase(onlinePlayers, arg);
	}

	@Override
	public boolean isValid(String arg, CommandSender sender) {
		return Bukkit.getServer().getPlayer(arg) != null;
	}

	@Override
	public Player read(String arg, CommandSender sender) throws TypeException {
		return Bukkit.getServer().getPlayer(arg);
	}
}
