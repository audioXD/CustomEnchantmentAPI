package adx.audioxd.customenchantmentapi.commands.requirement;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import org.bukkit.command.CommandSender;

public class RequirementIsOP implements Requirement {
	private static RequirementIsOP i = new RequirementIsOP();
	public static RequirementIsOP get(){ return i; }

	RequirementIsOP(){}

	@Override
	public boolean apply(CommandSender sender, CEAPICommand command) {
		return sender.isOp();
	}

	@Override
	public String createErrorMessage(CommandSender sender) {
		return createErrorMessage(sender, null);
	}

	@Override
	public String createErrorMessage(CommandSender sender, CEAPICommand command) {
		if(command == null) CustomEnchantmentAPI.getInstance().getLanguageConfig().NOT_PLAYER.format("op", "");
		return CustomEnchantmentAPI.getInstance().getLanguageConfig().NO_PERMISSION.format("op", command.getFullCommand());
	}
}
