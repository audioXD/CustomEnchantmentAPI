package adx.audioxd.customenchantmentapi.listeners;


import adx.audioxd.customenchantmentapi.CustomEnchantmentAPI;
import adx.audioxd.customenchantmentapi.EnchantmentRegistery;
import adx.audioxd.customenchantmentapi.enchantment.Enchanted;
import adx.audioxd.customenchantmentapi.enchantment.Enchantment;
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
import org.bukkit.metadata.FixedMetadataValue;

public class onBowShotEvent extends CEPLListener {

	// Global fields
	private static String salt = "adx_536_";
// End of Global Fields

	// Constructor
	public onBowShotEvent(CustomEnchantmentAPI plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void bowShoot(
			EntityShootBowEvent event) {
		for(Enchanted ench : getEnchantments(event.getBow())) {

			event.getProjectile().setMetadata(
					salt + ench.getEncnantment().getName(),
					new FixedMetadataValue(this.plugin, ench.getLvl())
			);

			EBowShootEvent e = new EBowShootEvent(ench.getLvl(), event.getBow(), event.getEntity(),
			                                      event.getProjectile()
			);
			e.setCancelled(event.isCancelled());
			ench.fireEvent(e);
			event.setCancelled(e.isCancelled());

		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void hitedByArrow(
			EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Arrow)) return;
		if(!(event.getEntity() instanceof LivingEntity)) return;

		LivingEntity targer = (LivingEntity) event.getEntity();
		Arrow arrow = (Arrow) event.getDamager();

		for(Enchantment ench : EnchantmentRegistery.geEnchantmentsArray()) {
			if(arrow.hasMetadata(salt + ench.getName())) {
				int lvl = arrow.getMetadata(salt + ench.getName()).get(0).asInt();
				EArrowHitEvent e = new EArrowHitEvent(lvl, targer, arrow, event.getDamage());
				e.setCancelled(event.isCancelled());
				{
					ench.fireEvent(e);
				}
				event.setDamage(e.getDamage());
				event.setCancelled(e.isCancelled());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void arrowLandEvent(ProjectileHitEvent event) {
		if(!(event.getEntity() instanceof Arrow)) return;
		Arrow arrow = (Arrow) event.getEntity();

		for(Enchantment ench : EnchantmentRegistery.geEnchantmentsArray()) {
			if(arrow.hasMetadata(salt + ench.getName())) {
				int lvl = arrow.getMetadata(salt + ench.getName()).get(0).asInt();

				EArrowLandEvent e = new EArrowLandEvent(lvl, arrow);
				ench.fireEvent(e);
			}
		}
	}

}
