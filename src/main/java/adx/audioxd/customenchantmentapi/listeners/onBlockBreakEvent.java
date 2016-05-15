package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.events.world.EBlockBreakEvent;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class onBlockBreakEvent extends CEPLListener {
	// Constructor
	public onBlockBreakEvent(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		EBlockBreakEvent eEvent = new EBlockBreakEvent(ItemUtil.getMainHandItem(event.getPlayer()),
		                                               event.getPlayer(), event.getBlock(), event.getExpToDrop()
		);
		eEvent.setCancelled(event.isCancelled());
		{
			EnchantmentRegistry.fireEvents(getEnchantments(eEvent.getItem()), eEvent);
		}
		event.setCancelled(eEvent.isCancelled());
		event.setExpToDrop(eEvent.getExpToDrop());
	}

}
