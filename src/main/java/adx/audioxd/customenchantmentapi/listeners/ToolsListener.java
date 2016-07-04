package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;
import adx.audioxd.customenchantmentapi.events.world.EBlockBreakEvent;
import adx.audioxd.customenchantmentapi.events.world.EBlockDamageEvent;
import adx.audioxd.customenchantmentapi.events.world.EBlockPlaceEvent;
import adx.audioxd.customenchantmentapi.events.world.EInteractEvent;
import adx.audioxd.customenchantmentapi.listeners.extra.EEquip;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class ToolsListener extends CEAPIListenerUtils {

	// ---------------------------------------------------------- //
	//                      CONSTRUCTOR                           //
	// ---------------------------------------------------------- //

	public ToolsListener(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	// ---------------------------------------------------------- //
	//                     BLOCK EVENTS                           //
	// ---------------------------------------------------------- //

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDamageBlock(BlockDamageEvent event) {
		if(event.getBlock() == null || event.getBlock().getType() == null || event.getPlayer() == null || ItemUtil.isEmpty(event.getItemInHand()))
			return;

		EBlockDamageEvent e = new EBlockDamageEvent(event.getPlayer(), event.getItemInHand(), event.getBlock());
		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getItemInHand()), e);
		event.setCancelled(e.isCancelled());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock() == null || event.getBlock().getType() == null || event.getPlayer() == null) return;

		ItemStack item = ItemUtil.getMainHandItem(event.getPlayer());
		if(ItemUtil.isEmpty(item)) return;

		EBlockBreakEvent eEvent = new EBlockBreakEvent(item, event.getPlayer(), event.getBlock(), event.getExpToDrop());
		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(eEvent.getItem()), eEvent);
		event.setCancelled(eEvent.isCancelled());
		event.setExpToDrop(eEvent.getExpToDrop());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getBlock() == null || event.getBlock().getType() == null || event.getPlayer() == null || ItemUtil.isEmpty(event.getItemInHand()))
			return;

		EBlockPlaceEvent eEvent = new EBlockPlaceEvent(event.getItemInHand(), event.getPlayer(), event.getBlock(), event.getBlockAgainst(), event.getBlockPlaced(), event.getBlockReplacedState());
		eEvent.setBuild(event.canBuild());

		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(eEvent.getItem()), eEvent);

		event.setCancelled(eEvent.isCancelled());
		event.setBuild(eEvent.canBuild());
	}



	@EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent event) {
		if(event.getPlayer() == null || event.getAction() == null || ItemUtil.isEmpty(event.getItem())) return;

		HandType hT = CustomEnchantmentAPI.getInstance().getNSM().isHandMainHAnd(event) ? HandType.MAIN : HandType.OFF;

		EInteractEvent e = new EInteractEvent(event.getItem(), event.getPlayer(), event.getAction(), event.getBlockFace(), event.getClickedBlock(), hT);

		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getItem()), e);

		event.setCancelled(e.isCancelled());
	}
}
