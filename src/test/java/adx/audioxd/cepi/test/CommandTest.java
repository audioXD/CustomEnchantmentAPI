package adx.audioxd.cepi.test;


import adx.audioxd.customenchantmentapi.commands.ceapi.Ceapi;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class CommandTest {

  @org.junit.Test
  public void bla() {
    CommandSender sender = new CommandSender() {
      @Override
      public void sendMessage(String message) {

      }

      @Override
      public void sendMessage(String[] messages) {

      }

      @Override
      public Server getServer() {
        return null;
      }

      @Override
      public String getName() {
        return null;
      }

      @Override
      public boolean isPermissionSet(String name) {
        return false;
      }

      @Override
      public boolean isPermissionSet(Permission perm) {
        return false;
      }

      @Override
      public boolean hasPermission(String name) {
        if (name.equalsIgnoreCase("adx.ceapi.use")) return true;
        return false;
      }

      @Override
      public boolean hasPermission(Permission perm) {
        return false;
      }

      @Override
      public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return null;
      }

      @Override
      public PermissionAttachment addAttachment(Plugin plugin) {
        return null;
      }

      @Override
      public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value,
          int ticks) {
        return null;
      }

      @Override
      public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return null;
      }

      @Override
      public void removeAttachment(PermissionAttachment attachment) {

      }

      @Override
      public void recalculatePermissions() {

      }

      @Override
      public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null;
      }

      @Override
      public boolean isOp() {
        return false;
      }

      @Override
      public void setOp(boolean value) {

      }
    };
    List<String> args = new ArrayList<>();
    args.add("");

    System.out.println(new Ceapi().getTabCompletions(args, sender));
    System.out.println(new Ceapi().isVisibleTo(sender));
    System.out.println(new Ceapi().getSubCommand("list").isVisibleTo(sender));
  }
}
