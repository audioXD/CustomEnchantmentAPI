package adx.audioxd.customenchantmentapi.commands.requirement;


import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import org.bukkit.command.CommandSender;

public interface Requirement {
	boolean apply(CommandSender sender, CEAPICommand command);

	String createErrorMessage(CommandSender sender);
	String createErrorMessage(CommandSender sender, CEAPICommand command);
}
