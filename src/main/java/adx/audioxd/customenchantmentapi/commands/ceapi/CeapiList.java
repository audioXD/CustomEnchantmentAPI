package adx.audioxd.customenchantmentapi.commands.ceapi;


import adx.audioxd.customenchantmentapi.CEAPIPermissions;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.RegisteredEnchantment;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.exceptions.CEAPICommandException;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasPerm;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CeapiList extends CEAPICommand {
	public CeapiList() {
		this.addAlias("list");

		this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.LIST));
	}

	@Override
	public void perform() throws CEAPICommandException {
		sender.sendMessage(ChatColor.GREEN + "============[Enchantments]============");
		for(Plugin plugin : EnchantmentRegistry.getEnchantments().keySet()) {
			sender.sendMessage(ChatColor.BOLD + "[" + plugin.getName() + "]");
			Map<String, RegisteredEnchantment> data = EnchantmentRegistry.getEnchantments().get(plugin);
			if(data == null) continue;

			List<Enchantment> active = Arrays.asList(EnchantmentRegistry.getEnchantmentsArray());

			for(String id : data.keySet()) {
				Enchantment ench = data.get(id).getEnchantment();
				if(ench == null) continue;

				sender.sendMessage(ChatColor.GOLD + " - " + ench.getDisplay("") + " : " + (active.contains(ench) ? ChatColor.GREEN + "Active" : ChatColor.DARK_RED + "Disabled"));
			}
		}
		sender.sendMessage(ChatColor.RED + "================[END]================");
	}
}
