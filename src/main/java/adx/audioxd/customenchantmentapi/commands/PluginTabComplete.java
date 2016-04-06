package adx.audioxd.customenchantmentapi.commands;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistery;
import adx.audioxd.customenchantmentapi.config.LanguageConfig;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.listeners.CEPLListener;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PluginTabComplete implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> out = new ArrayList<String>();
		if(!sender.hasPermission("adx.ceapi.use"))
			return null;

		if(args.length == 1) {
			if("list".startsWith(args[0].toLowerCase()) && sender.hasPermission("adx.ceapi.list"))
				out.add("list");
			if("enchant".startsWith(args[0].toLowerCase()) && sender.hasPermission("adx.ceapi.enchant.use"))
				out.add("enchant");
			if("unenchant".startsWith(args[0].toLowerCase()) && sender.hasPermission("adx.ceapi.unenchant.use"))
				out.add("unenchant");
			if("reloadconfig".startsWith(args[0].toLowerCase()) && sender.isOp())
				out.add("reloadConfigs");
		} else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("enchant") || args[0].equalsIgnoreCase("unenchant")) {
				for(Plugin plugin : EnchantmentRegistery.getEnchantments().keySet()) {
					if(EnchantmentRegistery.getEnchantments().get(plugin) == null) continue;

					for(String enchName : EnchantmentRegistery.getEnchantments().get(plugin).keySet()) {
						Enchantment ench = EnchantmentRegistery.getEnchantments().get(plugin).get(enchName);
						String o = EnchantmentRegistery.getID(plugin, ench);
						if(o == null) continue;
						if(o.toLowerCase().startsWith(args[1].toLowerCase())) out.add(o);
					}
				}
			}
		}

		return out.isEmpty() ? null : out;
	}

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return PluginCommand.onCommand(arg0, arg1, arg2, arg3);
	}

	public static class PluginCommand {

		// Methods
		public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			LanguageConfig lc = CustomEnchantmentAPI.getInstace().getLanguageConfig();

			if(!sender.hasPermission("adx.ceapi.use")) {
				sender.sendMessage(lc.NO_PERMISSION.format("adx.ceapi.use", label));
				return false;
			}
			if(args.length <= 0) {
				sender.sendMessage(lc.NOT_ENOUGHT_ARGUMENTS.format());
				return false;
			}
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("list")) {
					if(sender.hasPermission("adx.ceapi.list")) {
						list(sender);
						return true;
					} else {
						sender.sendMessage(lc.NO_PERMISSION.format("adx.ceapi.list", label + " " + args[0]));
						return false;
					}
				} else if(args[0].equalsIgnoreCase("enchant")) {
					if(sender.hasPermission("adx.ceapi.enchant.use")) {
						return enchannt(sender, cmd, label, args);
					} else {
						sender.sendMessage(lc.NO_PERMISSION.format("adx.ceapi.enchant.use", label + " " + args[0]));
						return false;
					}
				} else if(args[0].equalsIgnoreCase("unenchant")) {
					if(sender.hasPermission("adx.ceapi.unenchant.use")) {
						unenchannt(sender, cmd, label, args);
					} else {
						sender.sendMessage(lc.NO_PERMISSION.format("adx.ceapi.unenchant.use", label + " " + args[0]));
						return false;
					}
				} else if(args[0].equalsIgnoreCase("reloadConfigs")) {
					if(sender.isOp()) {
						CustomEnchantmentAPI.getInstace().reloadConfigs();
						sender.sendMessage(
								CustomEnchantmentAPI.getInstace().getLanguageConfig().RELOAD_CONFIG.format());
					} else {
						sender.sendMessage(lc.NO_PERMISSION.format("op", label + " " + args[0]));
					}
				} else {
					sender.sendMessage(lc.UNKNOWN_COMMAND.format(label + " " + args[0]));
					return false;
				}
			}

			return false;
		}

		private static void list(CommandSender sender) {
			sender.sendMessage(ChatColor.GREEN + "============[Enchantments]============");
			for(Plugin plugin : EnchantmentRegistery.getEnchantments().keySet()) {
				sender.sendMessage(ChatColor.BOLD + "[" + plugin.getName() + "]");
				Map<String, Enchantment> data = EnchantmentRegistery.getEnchantments().get(plugin);
				if(data == null) continue;
				for(String id : data.keySet()) {
					Enchantment ench = data.get(id);
					if(ench == null) continue;
					sender.sendMessage(ChatColor.GOLD + " - " + ench.getDisplay(""));
				}
			}
			sender.sendMessage(ChatColor.RED + "================[END]================");
		}

		private static boolean enchannt(CommandSender sender, Command cmd, String label, String[] args) {
			LanguageConfig lc = CustomEnchantmentAPI.getInstace().getLanguageConfig();

			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length >= 2) {
					Enchantment ench = EnchantmentRegistery.getFromID(args[1]);
					if(ench != null) {
						if(!(player.hasPermission("adx.ceapi.enchant." + args[1].toLowerCase()) || player
								.hasPermission("adx.ceapi.enchant.*"))) {
							sender.sendMessage(lc.ENCHANT_NO_ACCES_TO_ENCHANTMENT
									                   .format("adx.ceapi.enchant." + args[1].toLowerCase(), ench.getDisplay("")));
							return false;
						}

						int lvl = (args.length == 2) ?
								1 :
								((StringUtils.isNumeric(args[2])) ? Integer.valueOf(args[2]) : 1);
						if(lvl < 1) {
							sender.sendMessage(lc.LEVEL_LESS_THAN_ONE.format());
							return false;
						}

						if(EnchantmentRegistery.enchant(ItemUtil.getMainHandItem(player), ench, lvl, true, false)) {
							CEPLListener.itemInHand(player, ItemUtil.getMainHandItem(player), HandType.MAIN);
							sender.sendMessage(lc.ENCHANT_SUCCES.format(ench.getDisplay(lvl)));
						} else {
							sender.sendMessage(lc.ENCHANT_ERROR.format(ench.getDisplay(lvl)));
						}
						return false;
					}

					sender.sendMessage(lc.UNKNOWN_ENCHANTMENT.format(args[1]));
					return false;
				} else {
					sender.sendMessage(lc.NOT_ENOUGHT_ARGUMENTS.format());
					return false;
				}

			} else {
				sender.sendMessage(lc.NOT_PLAYER.format(label));
				return false;
			}
		}

		private static boolean unenchannt(CommandSender sender, Command cmd, String label, String[] args) {
			LanguageConfig lc = CustomEnchantmentAPI.getInstace().getLanguageConfig();

			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length >= 2) {
					Enchantment ench = EnchantmentRegistery.getFromID(args[1]);
					if(ench != null) {
						if(!(player.hasPermission("adx.ceapi.unenchant." + args[1].toLowerCase()) || player
								.hasPermission("adx.ceapi.unenchant.*"))) {
							sender.sendMessage(lc.UNENCHANT_NO_ACCES_TO_ENCHANTMENT
									                   .format("adx.ceapi.unenchant." + args[1].toLowerCase(), ench.getDisplay("")));
							return false;
						}
						ItemStack prew = new ItemStack(ItemUtil.getMainHandItem(player));
						if(EnchantmentRegistery.unenchant(ItemUtil.getMainHandItem(player), ench)) {
							CEPLListener.itemNotInHand(player, prew, HandType.MAIN);
							sender.sendMessage(lc.UNENCHANT_SUCCES.format(ench.getDisplay("")));
						} else {
							sender.sendMessage(lc.UNENCHANT_ERROR.format(ench.getDisplay("")));
						}
						return false;
					}
					sender.sendMessage(lc.UNKNOWN_ENCHANTMENT.format(args[1]));
					return false;
				} else {
					sender.sendMessage(lc.NOT_ENOUGHT_ARGUMENTS.format());
					return false;
				}

			} else {
				sender.sendMessage(lc.NOT_PLAYER.format(label));
				return false;
			}
		}
	}
}
