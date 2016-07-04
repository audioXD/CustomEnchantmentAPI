package adx.audioxd.customenchantmentapi.commands.ceapi;


import adx.audioxd.customenchantmentapi.CEAPIPermissions;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasPerm;

public class Ceapi extends CEAPICommand {
	public Ceapi() {
		this.addAlias("ceapi");
		this.addAlias("CustomEnchantmentAPI");

		this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.USE));

		this.addSubCommands(new CeapiList());
		this.addSubCommands(new CeapiEnchant());
		this.addSubCommands(new CeapiUnenchnat());
		this.addSubCommands(new CeapiReloadConfig());
	}
}
