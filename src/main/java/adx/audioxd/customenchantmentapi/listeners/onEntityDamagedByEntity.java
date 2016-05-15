package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedByEntityEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent.Type;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagesEntityEvent;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class onEntityDamagedByEntity extends CEPLListener {
	// Constructor
	public onEntityDamagedByEntity(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onOwnerDamagedByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() == null) return;
		if(!(event.getEntity() instanceof LivingEntity)) return;

		LivingEntity owner = (LivingEntity) event.getEntity();
		Entity damager = event.getDamager();

		// Main hand and off hand
		ItemStack mainHand = ItemUtil.getMainHandItem(owner), offHand = ItemUtil.getOffHandItem(owner);
		// Main hand
		if(ItemType.SHIELD.matchType(mainHand)) {
			EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(mainHand, owner,
			                                                              event.getDamage(), event.getCause(), Type.SHIELD, damager
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(mainHand), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		} else {
			EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(mainHand, owner,
			                                                              event.getDamage(), event.getCause(), Type.IN_HAND, damager
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(mainHand), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		}
		// OFf hand
		if(ItemType.SHIELD.matchType(offHand)) {
			EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(offHand, owner,
			                                                              event.getDamage(), event.getCause(), Type.SHIELD, damager
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(offHand), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		} else {
			EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(offHand, owner,
			                                                              event.getDamage(), event.getCause(), Type.IN_HAND, damager
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(offHand), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		}
		// Armor
		for(ItemStack item : owner.getEquipment().getArmorContents()) {
			EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(item, owner,
			                                                              event.getDamage(), event.getCause(), Type.ARMOR, damager
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(item), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		}

		/// for horses
		if(owner instanceof Horse) {
			Horse horse = (Horse) owner;

			{
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(horse.getInventory().getArmor(), horse, event.getDamage(), event.getCause(), Type.HORSE_ARMOR,
				                                                              damager
				);
				e.setCancelled(event.isCancelled());
				{
					EnchantmentRegistry.fireEvents(getEnchantments(horse.getInventory().getArmor()), e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
			{
				EOwnerDamagedByEntityEvent e = new EOwnerDamagedByEntityEvent(horse.getInventory().getSaddle(), horse, event.getDamage(), event.getCause(), Type.SADDLE,
				                                                              damager
				);
				e.setCancelled(event.isCancelled());
				{
					EnchantmentRegistry.fireEvents(getEnchantments(horse.getInventory().getSaddle()), e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onOwnerDamagesEntity(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof LivingEntity)) return;
		LivingEntity owner = (LivingEntity) event.getDamager();
		Entity entity = event.getEntity();

		ItemStack item = ItemUtil.getMainHandItem(owner);

		EOwnerDamagesEntityEvent e = new EOwnerDamagesEntityEvent(item, owner, entity,
		                                                          event.getDamage(), event.getCause()
		);
		e.setCancelled(event.isCancelled());
		{
			EnchantmentRegistry.fireEvents(getEnchantments(item), e);
		}
		event.setCancelled(e.isCancelled());
		event.setDamage(e.getDamage());
	}

}
