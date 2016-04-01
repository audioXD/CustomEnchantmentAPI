package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.events.world.EBlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class onBlockPlaceEvent extends CEPLListener {

	public onBlockPlaceEvent(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		for(Enchanted ench : getEnchantments(event.getItemInHand())) {
			EBlockPlaceEvent e = new EBlockPlaceEvent(ench.getLvl(), event.getItemInHand(), event.getPlayer(),
			                                          event.getBlock(), event.getBlockAgainst(), event.getBlockPlaced(), event.getBlockReplacedState()
			);
			{
				ench.fireEvent(e);
			}
			event.setCancelled(e.isCancelled());
			event.setBuild(e.canBuild());

		}
	}

}
