package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.events.world.EBlockBreakEvent;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class onBlockBreakEvent extends CEPLListener {
	public onBlockBreakEvent(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		for(Enchanted ench : getEnchantments(ItemUtil.getMainHandItem(event.getPlayer()))) {
			EBlockBreakEvent e = new EBlockBreakEvent(ench.getLvl(), ItemUtil.getMainHandItem(event.getPlayer()),
			                                          event.getPlayer(), event.getBlock(), event.getExpToDrop()
			);
			{
				ench.fireEvent(e);
			}
			event.setCancelled(e.isCancelled());
			event.setExpToDrop(e.getExpToDrop());
		}
	}

}
