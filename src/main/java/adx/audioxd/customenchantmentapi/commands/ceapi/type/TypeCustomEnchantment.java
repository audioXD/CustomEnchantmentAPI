package adx.audioxd.customenchantmentapi.commands.ceapi.type;


import adx.audioxd.customenchantmentapi.CEAPIPermissions;
import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.commands.exceptions.TypeException;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasPerm;
import adx.audioxd.customenchantmentapi.commands.type.TypeAbstract;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeCustomEnchantment extends TypeAbstract<Enchantment> {
	private static final TypeCustomEnchantment i = new TypeCustomEnchantment();
	public static TypeCustomEnchantment get() { return i; }
	TypeCustomEnchantment() {}

	@Override
	public Enchantment read(String arg, CommandSender sender) throws TypeException {
		if(!hasPermission(sender, arg)) throw new TypeException(RequirementHasPerm.get(CEAPIPermissions.ENCHANTMENT_PREFIX + arg).createErrorMessage(sender));
		return EnchantmentRegistry.getFromID(arg);
	}

	@Override
	public String getInvelidErrorMessage(String arg) {
		return CustomEnchantmentAPI.getInstance().getLanguageConfig().UNKNOWN_ENCHANTMENT.format(arg);
	}

	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> out = new ArrayList<>();

		for(Plugin plugin : EnchantmentRegistry.getEnchantments().keySet()) {
			if(EnchantmentRegistry.getEnchantments().get(plugin) == null) continue;
			for(String enchName : EnchantmentRegistry.getEnchantments().get(plugin).keySet()) {
				Enchantment ench = EnchantmentRegistry.getEnchantments().get(plugin).get(enchName);
				String o = EnchantmentRegistry.getID(plugin, ench);
				if(o == null) continue;
				if(!hasPermission(sender, o)) continue;
				if(o.toLowerCase().startsWith(arg.toLowerCase())) out.add(o);
			}
		}
		return out;
	}

	private static boolean hasPermission(CommandSender sender, String o) {
		return RequirementHasPerm.get(CEAPIPermissions.ENCHANTMENT_PREFIX + o).apply(sender)
				|| RequirementHasPerm.get(CEAPIPermissions.ENCHANTMENT_ALL).apply(sender);
	}
}
