package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.enums.ItemType;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent;
import adx.audioxd.customenchantmentapi.events.damage.EOwnerDamagedEvent.Type;
import adx.audioxd.customenchantmentapi.utils.ItemUtil;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class onEntityDamage extends CEPLListener {
	// Constructor
	public onEntityDamage(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof LivingEntity)) return;
		LivingEntity le = (LivingEntity) event.getEntity();

		// Main hand and off hand
		ItemStack mainHand = ItemUtil.getMainHandItem(le), offHand = ItemUtil.getOffHandItem(le);
		// Main hand
		if(ItemType.SHIELD.matchType(mainHand)) {
			EOwnerDamagedEvent e = new EOwnerDamagedEvent(mainHand, le, event.getDamage(),
			                                              event.getCause(), Type.SHIELD
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(mainHand), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());

		} else {
			EOwnerDamagedEvent e = new EOwnerDamagedEvent(mainHand, le, event.getDamage(),
			                                              event.getCause(), Type.IN_HAND
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
			EOwnerDamagedEvent e = new EOwnerDamagedEvent(offHand, le, event.getDamage(),
			                                              event.getCause(), Type.SHIELD
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(offHand), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		} else {
			EOwnerDamagedEvent e = new EOwnerDamagedEvent(offHand, le, event.getDamage(),
			                                              event.getCause(), Type.IN_HAND
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(offHand), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		}
		// Armor
		for(ItemStack item : le.getEquipment().getArmorContents()) {
			EOwnerDamagedEvent e = new EOwnerDamagedEvent(item, le, event.getDamage(),
			                                              event.getCause(), Type.ARMOR
			);
			e.setCancelled(event.isCancelled());
			{
				EnchantmentRegistry.fireEvents(getEnchantments(item), e);
			}
			event.setCancelled(e.isCancelled());
			event.setDamage(e.getDamage());
		}
		/// for horses
		if(le instanceof Horse) {
			Horse horse = (Horse) le;
			{
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(horse.getInventory().getArmor(), horse,
				                                              event.getDamage(), event.getCause(), Type.HORSE_ARMOR
				);
				e.setCancelled(event.isCancelled());
				{
					EnchantmentRegistry.fireEvents(getEnchantments(horse.getInventory().getArmor()), e);
				}
				event.setCancelled(e.isCancelled());
				event.setDamage(e.getDamage());
			}
			{
				EOwnerDamagedEvent e = new EOwnerDamagedEvent(horse.getInventory().getSaddle(), horse,
				                                              event.getDamage(), event.getCause(), Type.SADDLE
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
}
