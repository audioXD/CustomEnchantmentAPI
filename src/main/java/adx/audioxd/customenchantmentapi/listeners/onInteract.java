package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.events.world.EInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class onInteract extends CEPLListener {

	public onInteract(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onInteractEvent(
			PlayerInteractEvent event) {
		if(event.isCancelled() && !(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction()
				.equals(Action.LEFT_CLICK_AIR))) return;
		if(event.getAction() == null) return;
		if(event.getPlayer() == null) return;
		if(event.getItem() == null) return;

		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		HandType hT = CustomEnchantmentAPI.getInstace().getNSM().isHandMainHAnd(event) ? HandType.MAIN : HandType.OFF;

		for(Enchanted ench : getEnchantments(item)) {
			EInteractEvent e = new EInteractEvent(ench.getLvl(), item, player, event.getAction(), event.getBlockFace(),
			                                      event.getClickedBlock(), hT
			);
			ench.fireEvent(e);
			if(e.isCancelled()) event.setCancelled(true);
		}
	}

}
