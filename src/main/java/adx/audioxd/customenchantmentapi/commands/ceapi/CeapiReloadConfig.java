package adx.audioxd.customenchantmentapi.commands.ceapi;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.exceptions.CEAPICommandException;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementIsOP;

public class CeapiReloadConfig extends CEAPICommand {
	public CeapiReloadConfig() {
		this.addAlias("reloadConfigs");

		// this.addRequirements(RequirementHasPerm.get("adx.ceapi.use"));
		this.addRequirements(RequirementIsOP.get());
	}

	@Override
	public void perform() throws CEAPICommandException {
		CustomEnchantmentAPI.getInstance().reloadConfigs();
		sender.sendMessage(CustomEnchantmentAPI.getInstance().getLanguageConfig().RELOAD_CONFIG.format());
	}
}
