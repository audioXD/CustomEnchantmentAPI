package adx.audioxd.customenchantmentapi.events.world;


import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import adx.audioxd.customenchantmentapi.events.inventory.hand.enums.HandType;

public class EInteractEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private boolean cancelled = false;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	private Action action;
	public Action getAction() {
		return action;
	}

	private BlockFace blockFace;
	public BlockFace getBlockFace() {
		return blockFace;
	}

	private Block clickedBlock;
	public Block getClickedBlock() {
		return clickedBlock;
	}

	private final HandType handType;
	public HandType getHandType() {
		return handType;
	}

	public EInteractEvent(int lvl, ItemStack item, LivingEntity owner, Action action, BlockFace blockFace,
			Block clickedBlock, HandType handType) {
		super(lvl, owner, item);
		this.action = action;
		this.blockFace = blockFace;
		this.clickedBlock = clickedBlock;
		this.handType = handType;
	}
}
