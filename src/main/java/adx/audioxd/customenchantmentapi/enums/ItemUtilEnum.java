package adx.audioxd.customenchantmentapi.enums;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemUtilEnum {
	// OTHER
	OTHER_UNSTACKABLE_ITEMS(new Material[] {
			Material.ENCHANTED_BOOK,
			Material.POTION,
			Material.BOOK_AND_QUILL,
			Material.FLINT_AND_STEEL,
			Material.MINECART,
			Material.COMMAND_MINECART,
			Material.EXPLOSIVE_MINECART,
			Material.HOPPER_MINECART,
			Material.POWERED_MINECART,
			Material.STORAGE_MINECART,
			Material.WATER_BUCKET,
			Material.LAVA_BUCKET,
			Material.MILK_BUCKET,
			Material.MAP,
			Material.SADDLE,
			Material.CARROT_STICK,
			Material.BOAT,
			Material.RECORD_10,
			Material.RECORD_11,
			Material.RECORD_12,
			Material.RECORD_9,
			Material.RECORD_8,
			Material.RECORD_7,
			Material.RECORD_6,
			Material.RECORD_5,
			Material.RECORD_4,
			Material.RECORD_3,
			Material.GOLD_RECORD,
			Material.GREEN_RECORD
	}),

	INTERFACABLE_TILE_ENTITIES(new Material[] {
			Material.DISPENSER,
			Material.NOTE_BLOCK,
			Material.BED_BLOCK,
			Material.CHEST,
			Material.WORKBENCH,
			Material.FURNACE,
			Material.BURNING_FURNACE,
			Material.WOODEN_DOOR,
			Material.ACACIA_DOOR,
			Material.BIRCH_DOOR,
			Material.DARK_OAK_DOOR,
			Material.IRON_DOOR,
			Material.JUNGLE_DOOR,
			Material.SPRUCE_DOOR,
			Material.LEVER,
			Material.REDSTONE_ORE,
			Material.STONE_BUTTON,
			Material.JUKEBOX,
			Material.CAKE_BLOCK,
			Material.DIODE_BLOCK_ON,
			Material.DIODE_BLOCK_OFF,
			Material.TRAP_DOOR,
			Material.FENCE_GATE,
			Material.ENCHANTMENT_TABLE,
			Material.BREWING_STAND,
			Material.DRAGON_EGG,
			Material.ENDER_CHEST,
			Material.COMMAND,
			Material.BEACON,
			Material.WOOD_BUTTON,
			Material.ANVIL,
			Material.TRAPPED_CHEST,
			Material.REDSTONE_COMPARATOR_ON,
			Material.REDSTONE_COMPARATOR_OFF,
			Material.HOPPER,
			Material.ACACIA_FENCE_GATE,
			Material.BIRCH_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE,
			Material.FENCE_GATE,
			Material.JUNGLE_FENCE_GATE,
			Material.SPRUCE_FENCE_GATE,
			Material.DROPPER

	});

	private final Material[] types;
	private final List<Material> typesList;

	ItemUtilEnum(ItemUtilEnum[] sub) {
		List<Material> materials = new ArrayList<Material>();
		for (ItemUtilEnum type : sub) {
			materials.addAll(type.getTypesList());
		}

		Collections.sort(materials);

		this.typesList = materials;
		this.types = materials.toArray(new Material[materials.size()]);
	}

	public boolean matchType(ItemStack item) {
		if (adx.audioxd.customenchantmentapi.utils.ItemUtil.isEmpty(item)) return false;
		return typesList.contains(item.getType());
	}

	public boolean matchType(Material material) {
		if (material == null) return false;
		return typesList.contains(material);
	}

	ItemUtilEnum(Material[] types) {
		this.types = types;
		this.typesList = Arrays.asList(types);
	}

	ItemUtilEnum(List<Material> typesList) {
		this.typesList = typesList;
		this.types = typesList.toArray(new Material[typesList.size()]);
	}

	public Material[] getTypes() {
		return types;
	}

	public List<Material> getTypesList() {
		return typesList;
	}
}
