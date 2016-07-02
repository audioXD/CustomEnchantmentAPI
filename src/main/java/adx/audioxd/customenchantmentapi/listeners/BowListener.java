package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistry;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.events.bow.EArrowHitEvent;
import adx.audioxd.customenchantmentapi.events.bow.EArrowLandEvent;
import adx.audioxd.customenchantmentapi.events.bow.EBowShootEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class BowListener extends CEAPIListenerUtils {
	private static String salt = "adx_536_";

	// ---------------------------------------------------------- //
	//                      CONSTRUCTOR                           //
	// ---------------------------------------------------------- //

	public BowListener(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	// ---------------------------------------------------------- //
	//                 EVENTS FOR CUSTOM BOWS                     //
	// ---------------------------------------------------------- //

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void bowShoot(EntityShootBowEvent event) {
		EBowShootEvent eEvent = new EBowShootEvent(event.getBow(), event.getEntity(),
		                                           event.getProjectile()
		);
		eEvent.setCancelled(event.isCancelled());
		{
			for(Enchanted ench : EnchantmentRegistry.getEnchantments(event.getBow())) {
				ench.fireEvent(eEvent);
				EnchantmentRegistry.enchant(event.getProjectile(), ench.getEnchantment(), ench.getLvl(), false, false);
			}
		}
		event.setCancelled(eEvent.isCancelled());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void hitByArrow(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Arrow)) return;
		if(!(event.getEntity() instanceof LivingEntity)) return;

		LivingEntity target = (LivingEntity) event.getEntity();
		Arrow arrow = (Arrow) event.getDamager();

		EArrowHitEvent eEvent = new EArrowHitEvent(target, arrow, event.getDamage());
		eEvent.setCancelled(event.isCancelled());
		{
			EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getDamager()), eEvent);
		}
		event.setDamage(eEvent.getDamage());
		event.setCancelled(eEvent.isCancelled());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void arrowLandEvent(ProjectileHitEvent event) {
		if(!(event.getEntity() instanceof Arrow)) return;
		Arrow arrow = (Arrow) event.getEntity();

		EArrowLandEvent eEvent = new EArrowLandEvent(arrow);
		EnchantmentRegistry.fireEvents(EnchantmentRegistry.getEnchantments(event.getEntity()), eEvent);
	}

}
