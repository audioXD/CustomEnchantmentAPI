package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.events.world.EBlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class onBlockPlaceEvent extends CEPLListener {
	// Constructor
	public onBlockPlaceEvent(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		EBlockPlaceEvent eEvent = new EBlockPlaceEvent(event.getItemInHand(), event.getPlayer(),
		                                               event.getBlock(), event.getBlockAgainst(), event.getBlockPlaced(), event.getBlockReplacedState()
		);
		eEvent.setBuild(event.canBuild());
		eEvent.setCancelled(event.isCancelled());
		{
			EnchantmentRegistry.fireEvents(getEnchantments(eEvent.getItem()), eEvent);
		}
		event.setCancelled(eEvent.isCancelled());
		event.setBuild(eEvent.canBuild());
	}

}
