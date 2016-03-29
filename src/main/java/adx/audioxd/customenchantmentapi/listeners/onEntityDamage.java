package adx.audioxd.customenchantmentapi.listeners;


import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent.Type;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;

public class onEntityDamage extends CEPLListener {

	public onEntityDamage(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) return;
		LivingEntity le = (LivingEntity) event.getEntity();

		// Main hand and off hand
		ItemStack mainHand = ItemUtil.getMainHandItem(le), offHand = ItemUtil.getOffHandItem(le);
		// Main hand
		if (ItemType.SHIELD.matchType(mainHand)) {
			for (Enchanted ench : this.getEnchantments(mainHand)) {
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(ench.getLvl(), mainHand, le, event.getDamage(),
						event.getCause(), Type.SHIELD);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		} else {
			for (Enchanted ench : this.getEnchantments(mainHand)) {
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(ench.getLvl(), mainHand, le, event.getDamage(),
						event.getCause(), Type.IN_HAND);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		}
		// OFf hand
		if (ItemType.SHIELD.matchType(offHand)) {
			for (Enchanted ench : this.getEnchantments(offHand)) {
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(ench.getLvl(), offHand, le, event.getDamage(),
						event.getCause(), Type.SHIELD);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		} else {
			for (Enchanted ench : this.getEnchantments(offHand)) {
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(ench.getLvl(), offHand, le, event.getDamage(),
						event.getCause(), Type.IN_HAND);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		}
		// Armor
		for (ItemStack item : le.getEquipment().getArmorContents()) {
			for (Enchanted ench : this.getEnchantments(item)) {
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(ench.getLvl(), item, le, event.getDamage(),
						event.getCause(), Type.ARMOR);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());

			}
		}
		/// for horses
		if (le instanceof Horse) {
			Horse horse = (Horse) le;

			for (Enchanted ench : this.getEnchantments(horse.getInventory().getArmor())) {
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(ench.getLvl(), horse.getInventory().getArmor(), horse,
						event.getDamage(), event.getCause(), Type.HORSE_ARMOR);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
			for (Enchanted ench : this.getEnchantments(horse.getInventory().getSaddle())) {
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(ench.getLvl(), horse.getInventory().getSaddle(), horse,
						event.getDamage(), event.getCause(), Type.SADDLE);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		}
	}
}
