package adx.audioxd.customenchantmentapi.commands.ceapi;


import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.exceptions.CEAPICommandException;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasItemInHand;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementIsPlayer;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;

/**
 * Created by test on 04/07/2016.
 */
public class CeapiListOnItem extends CEAPICommand {
	public CeapiListOnItem(){
		this.addAlias("check");

		this.addRequirements(RequirementIsPlayer.get());
		this.addRequirements(RequirementHasItemInHand.get());
	}

	@Override
	public void perform() throws CEAPICommandException {
		for(Enchanted ench: EnchantmentRegistry.getEnchantments(ItemUtil.getMainHandItem(this.getMe()))){
			this.getSender().sendMessage(ench.getEnchantment().getDisplay(ench.getLvl()));
		}
	}
}
