package adx.audioxd.customenchantmentapi.commands.ceapi;


import adx.audioxd.customenchantmentapi.CEAPIPermissions;
import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.ceapi.type.TypeCustomEnchantment;
import adx.audioxd.customenchantmentapi.commands.ceapi.type.TypeInteger;
import adx.audioxd.customenchantmentapi.commands.exceptions.CEAPICommandException;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasItemInHand;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasPerm;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementIsPlayer;
import adx.audioxd.customenchantmentapi.config.LanguageConfig;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.listeners.CEAPIListenerUtils;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;

public class CeapiEnchant extends CEAPICommand {
	public CeapiEnchant() {
		this.addAlias("enchant");

		this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.ENCHANT_USE));
		this.addRequirements(RequirementIsPlayer.get());
		this.addRequirements(RequirementHasItemInHand.get());

		this.addParameter(TypeCustomEnchantment.get());
		this.addParameter(TypeInteger.get()).setDefaultDesc("Default 1");
	}

	@Override
	public void perform() throws CEAPICommandException {
		if(sender == null) throw new CEAPICommandException("Sender is null");
		LanguageConfig lc = CustomEnchantmentAPI.getInstance().getLanguageConfig();

		Enchantment ench = this.readArg(null);
		if(ench == null) throw new CEAPICommandException(lc.UNKNOWN_ENCHANTMENT.format(this.readArgAt(0)));

		int lvl = this.readArg(1);
		if(lvl < 1) throw new CEAPICommandException(lc.LEVEL_LESS_THAN_ONE.format());

		if(EnchantmentRegistry.enchant(ItemUtil.getMainHandItem(me), ench, lvl, true, true)){
			CEAPIListenerUtils.itemInMainHand(me, ItemUtil.getMainHandItem(me));
			throw new CEAPICommandException(lc.ENCHANT_SUCCESS.format(ench.getDisplay(lvl)));
		}
		throw new CEAPICommandException(lc.ENCHANT_ERROR.format(ench.getDisplay(lvl)));
	}
}
