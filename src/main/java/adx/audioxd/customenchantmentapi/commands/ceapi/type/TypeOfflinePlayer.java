package adx.audioxd.customenchantmentapi.commands.ceapi.type;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.commands.exceptions.TypeException;
import adx.audioxd.customenchantmentapi.commands.type.TypeAbstract;
import adx.audioxd.customenchantmentapi.utils.Text;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class TypeOfflinePlayer extends TypeAbstract<OfflinePlayer> {

  private static final TypeOfflinePlayer i = new TypeOfflinePlayer();
  public static TypeOfflinePlayer get() {
    return i;
  }

  @Override
  public String getInvelidErrorMessage(String arg) {
    return CustomEnchantmentAPI.getInstance().getLanguageConfig().OFFLINE_PLAYER_NOT_FOUND
        .format(arg);
  }

  @Override
  public Collection<String> getTabList(CommandSender sender, String arg) {
    List<String> onlinePlayers = new ArrayList<>();
    for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
      onlinePlayers.add(player.getName());
    }
    return Text.getStartsWithIgnoreCase(onlinePlayers, arg);
  }

  @Override
  public boolean isValid(String arg, CommandSender sender) {
    return Bukkit.getServer().getOfflinePlayer(arg) != null;
  }

  @Override
  public OfflinePlayer read(String arg, CommandSender sender) throws TypeException {
    return Bukkit.getServer().getOfflinePlayer(arg);
  }
}
