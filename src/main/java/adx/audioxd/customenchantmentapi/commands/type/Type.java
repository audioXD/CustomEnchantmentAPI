package adx.audioxd.customenchantmentapi.commands.type;


import adx.audioxd.customenchantmentapi.commands.entities.Named;
import adx.audioxd.customenchantmentapi.commands.exceptions.TypeException;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.List;

public interface Type<T> extends Named{
	String getInvelidErrorMessage(String arg);

	String getName();

	T read(String arg, CommandSender sender) throws TypeException;
	T read(CommandSender sender) throws TypeException;
	T read(String arg) throws TypeException;
	T read() throws TypeException;

	boolean isValid(String arg, CommandSender sender);

	Collection<String> getTabList(CommandSender sender, String arg);
	List<String> getTabListFiltered(CommandSender sender, String arg);

	boolean allowSpaceAfterTab = false;
	boolean allowSpaceAfterTab();
	<T extends Type>T setAllowSpaceAfterTab(boolean allowSpaceAfterTab);

	boolean equals(T type1, T type2);
	boolean equalsInner(T type1, T type2);
}
