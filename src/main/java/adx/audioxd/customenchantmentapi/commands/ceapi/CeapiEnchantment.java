package adx.audioxd.customenchantmentapi.commands.ceapi;


import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.RegisteredEnchantment;
import adx.audioxd.customenchantmentapi.commands.CEAPICommand;
import adx.audioxd.customenchantmentapi.commands.ceapi.type.TypeCeapiEnchantmentOptions;
import adx.audioxd.customenchantmentapi.commands.ceapi.type.TypeCustomEnchantment;
import adx.audioxd.customenchantmentapi.commands.exceptions.CEAPICommandException;
import adx.audioxd.customenchantmentapi.commands.requirement.RequirementHasPerm;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
import adx.audioxd.customenchantmentapi.utils.Text;

public class CeapiEnchantment extends CEAPICommand {

  public CeapiEnchantment() {
    this.addAlias("enchantment");

    this.addRequirements(RequirementHasPerm.get("adx.ceapi.enchantment"));

    this.addParameter(TypeCustomEnchantment.get());
    this.addParameter("info", TypeCeapiEnchantmentOptions.get(), "SubCommand", "not null");
  }

  @Override
  public void perform() throws CEAPICommandException {
    RegisteredEnchantment registeredEnchantment = EnchantmentRegistry
        .getFromID(this.getArgs().get(0));
    Enchantment enchantment = registeredEnchantment.getEnchantment();

    sender.sendMessage(Text.parse(
        "&r -------------&6[&r" + enchantment.getDisplay("").trim() + "&6]&r-------------&r"));
    sender.sendMessage(Text.parse("Name: " + enchantment.getName()));
    sender.sendMessage(Text.parse("Active: " + registeredEnchantment.isActive()));
    sender.sendMessage(Text.parse("Priority: " + enchantment.getPriority()));
    sender.sendMessage(Text.parse("Max Level: " + enchantment.getMaxLvl()));
    sender.sendMessage(Text.parse("Description: " + enchantment.getDescription()));
  }
}
