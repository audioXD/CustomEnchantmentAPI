package adx.audioxd.customenchantmentapi.commands.requirement;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RequirementIsPlayer implements Requirement {

  private static RequirementIsPlayer i = new RequirementIsPlayer();
  public static RequirementIsPlayer get() {
    return i;
  }

  RequirementIsPlayer() {
  }

  @Override
  public boolean apply(CommandSender sender, CEAPICommand command) {
    return sender instanceof Player;
  }

  @Override
  public String createErrorMessage(CommandSender sender) {
    return createErrorMessage(sender, null);
  }

  @Override
  public String createErrorMessage(CommandSender sender, CEAPICommand command) {
    if (command == null)
      CustomEnchantmentAPI.getInstance().getLanguageConfig().NOT_PLAYER.format("");
    return CustomEnchantmentAPI.getInstance().getLanguageConfig().NOT_PLAYER
        .format(command.getFullCommand());
  }
}
