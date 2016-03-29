package adx.audioxd.customenchantmentapi.events.world;


import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;

public class EBlockPlaceEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private boolean canBuild = true;
	public boolean canBuild() {
		return canBuild;
	}
	public void setBuild(boolean build) {
		this.canBuild = build;
	}

	private Block block, blockAgainst, blockPlaced;
	public Block getBlock() {
		return block;
	}
	public Block getBlockAgainst() {
		return blockAgainst;
	}
	public Block getBlockPlaced() {
		return blockPlaced;
	}

	private BlockState blockReplacedState;
	public BlockState getBlockReplacedState() {
		return blockReplacedState;
	}

	private boolean cancelled = false;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public EBlockPlaceEvent(int lvl, ItemStack item, LivingEntity owner, Block block, Block blockAgainst,
			Block blockPlaced, BlockState blockReplacedState) {
		super(lvl, owner, item);
		this.block = block;
		this.blockAgainst = blockAgainst;
		this.blockPlaced = blockPlaced;
		this.blockReplacedState = blockReplacedState;
	}
}
