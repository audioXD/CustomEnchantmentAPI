package adx.audioxd.customenchantmentapi.events.world;


import adx.audioxd.customenchantmentapi.enchantment.event.extra.EnchantmentEventWithOwnerAndItem;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class EBlockBreakEvent extends EnchantmentEventWithOwnerAndItem implements Cancellable {
	private Block block;
	private int ExpToDrop = 0;
	private boolean cancelled = false;

	public EBlockBreakEvent(int lvl, ItemStack item, LivingEntity owner, Block block, int exp) {
		super(lvl, owner, item);
		this.block = block;
		this.ExpToDrop = exp;
	}

	public Block getBlock() {
		return block;
	}

	public int getExpToDrop() {
		return ExpToDrop;
	}

	public void setExpToDrop(int expToDrop) {
		ExpToDrop = expToDrop;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
