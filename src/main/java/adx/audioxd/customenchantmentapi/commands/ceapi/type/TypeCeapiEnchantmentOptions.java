package adx.audioxd.customenchantmentapi.commands.ceapi.type;


import adx.audioxd.customenchantmentapi.commands.exceptions.TypeException;
import adx.audioxd.customenchantmentapi.commands.type.TypeAbstract;
import org.bukkit.command.CommandSender;

public class TypeCeapiEnchantmentOptions extends TypeAbstract<String> {

  private static final TypeCeapiEnchantmentOptions i = new TypeCeapiEnchantmentOptions();
  public static TypeCeapiEnchantmentOptions get() {
    return i;
  }


  public static enum Options {

  }

  @Override
  public String getInvelidErrorMessage(String arg) {
    return null;
  }

  @Override
  public String read(String arg, CommandSender sender) throws TypeException {
    return arg;
  }
}
