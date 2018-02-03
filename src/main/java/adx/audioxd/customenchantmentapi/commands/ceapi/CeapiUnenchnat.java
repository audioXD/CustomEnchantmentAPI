package adx.audioxd.customenchantmentapi.commands.ceapi;


import adx.audioxd.customenchantmentapi.CEAPIPermissions;
import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.ceapi.type.TypeCustomEnchantment;
import adx.audioxd.customenchantmentapi.commands.exceptions.CEAPICommandException;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasItemInHand;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasPerm;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementIsPlayer;
import adx.audioxd.customenchantmentapi.config.LanguageConfig;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.listeners.CEAPIListenerUtils;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.inventory.ItemStack;

public class CeapiUnenchnat extends CEAPICommand {

  public CeapiUnenchnat() {
    this.addAlias("unenchant");

    this.addRequirements(RequirementHasPerm.get(CEAPIPermissions.UNENCHANT_USE));
    this.addRequirements(RequirementIsPlayer.get());
    this.addRequirements(RequirementHasItemInHand.get());

    this.addParameter(TypeCustomEnchantment.get());
  }

  @Override
  public void perform() throws CEAPICommandException {
    if (sender == null) throw new CEAPICommandException("Sender is null");
    LanguageConfig lc = CustomEnchantmentAPI.getInstance().getLanguageConfig();

    Enchantment ench = this.readArg();
    if (ench == null)
      throw new CEAPICommandException(lc.UNKNOWN_ENCHANTMENT.format(this.readArgAt(0)));

    ItemStack previous = new ItemStack(ItemUtil.getMainHandItem(me));
    if (EnchantmentRegistry.unenchant(ItemUtil.getMainHandItem(me), ench)) {
      CEAPIListenerUtils.itemNotInHand(me, previous, HandType.MAIN);
      throw new CEAPICommandException(lc.UNENCHANT_SUCCESS.format(ench.getDisplay("")));
    }
    throw new CEAPICommandException(lc.UNENCHANT_ERROR.format(ench.getDisplay("")));
  }
}
