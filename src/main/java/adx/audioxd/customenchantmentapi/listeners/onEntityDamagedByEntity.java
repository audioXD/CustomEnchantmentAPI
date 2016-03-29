package adx.audioxd.customenchantmentapi.listeners;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedByEntityEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent.Type;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagesEntityEvent;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;

public class onEntityDamagedByEntity extends CEPLListener {

	public onEntityDamagedByEntity(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onOwnerDamagedByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() == null) return;
		if (!(event.getEntity() instanceof LivingEntity)) return;

		LivingEntity owner = (LivingEntity) event.getEntity();
		Entity damager = event.getDamager();

		// Main hand and off hand
		ItemStack mainHand = ItemUtil.getMainHandItem(owner), offHand = ItemUtil.getOffHandItem(owner);
		// Main hand
		if (ItemType.SHIELD.matchType(mainHand)) {
			for (Enchanted ench : this.getEnchantments(mainHand)) {
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(ench.getLvl(), mainHand, owner,
						event.getDamage(), event.getCause(), Type.SHIELD, damager);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		} else {
			for (Enchanted ench : this.getEnchantments(mainHand)) {
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(ench.getLvl(), mainHand, owner,
						event.getDamage(), event.getCause(), Type.IN_HAND, damager);
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
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(ench.getLvl(), offHand, owner,
						event.getDamage(), event.getCause(), Type.SHIELD, damager);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		} else {
			for (Enchanted ench : this.getEnchantments(offHand)) {
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(ench.getLvl(), offHand, owner,
						event.getDamage(), event.getCause(), Type.IN_HAND, damager);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		}
		// Armor
		for (ItemStack item : owner.getEquipment().getArmorContents()) {
			for (Enchanted ench : this.getEnchantments(item)) {
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(ench.getLvl(), item, owner,
						event.getDamage(), event.getCause(), Type.ARMOR, damager);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());

			}
		}

		/// for horses
		if (owner instanceof Horse) {
			Horse horse = (Horse) owner;

			for (Enchanted ench : this.getEnchantments(horse.getInventory().getArmor())) {
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(ench.getLvl(),
						horse.getInventory().getArmor(), horse, event.getDamage(), event.getCause(), Type.HORSE_ARMOR,
						damager);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
			for (Enchanted ench : this.getEnchantments(horse.getInventory().getSaddle())) {
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(ench.getLvl(),
						horse.getInventory().getSaddle(), horse, event.getDamage(), event.getCause(), Type.SADDLE,
						damager);
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onOwnerDamagesEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof LivingEntity)) return;
		LivingEntity owner = (LivingEntity) event.getDamager();
		Entity entity = event.getEntity();

		ItemStack item = ItemUtil.getMainHandItem(owner);

		for (Enchanted ench : this.getEnchantments(item)) {
			EOwnerDamagesEntityEvent e = new EOwnerDamagesEntityEvent(ench.getLvl(), item, owner, entity,
					event.getDamage(), event.getCause());
			e.setCancelled(event.isCancelled());
			{
				ench.fireEvent(e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		}
	}

}
