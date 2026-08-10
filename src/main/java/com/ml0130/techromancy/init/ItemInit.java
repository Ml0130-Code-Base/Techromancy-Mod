package com.ml0130.techromancy.init;

import java.util.function.Function;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.item.ScannerItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			Techromancy.MOD_ID);

	// Magic Items
	public static final RegistryObject<Item> Solidified_Mana = register("solidified_mana", Item::new);
	public static final RegistryObject<Item> Imbued_Gear = register("imbued_gear", Item::new);
	public static final RegistryObject<Item> Imbued_Glass_Lens = register("imbued_glass_leans", Item::new);
	public static final RegistryObject<Item> Glass_Lens = register("glass_leans", Item::new);

	// Steam Items
	public static final RegistryObject<Item> Wooden_Gear = register("wooden_gear", Item::new);
	public static final RegistryObject<Item> Steel_Gear = register("steel_gear", Item::new);
	public static final RegistryObject<Item> Glass_Pipe = register("glass_pipe", Item::new);

	// Ores and Ingot
	public static final RegistryObject<Item> Steel_Ingot = register("steel_ingot", Item::new);
	public static final RegistryObject<Item> Steel_Nugget = register("steel_nugget", Item::new);

	// Research tools
	public static final RegistryObject<Item> Scanner = register("scanner", ScannerItem::new);

	// Tools (tool classes were removed in 1.21; tools are plain Items configured via Item.Properties)
	public static final RegistryObject<Item> Solidified_Mana_Pickaxe = register("solidified_mana_pickaxe",
			p -> new Item(p.pickaxe(ToolMaterial.DIAMOND, 1.0f, -2.8f)));
	public static final RegistryObject<Item> Solidified_Mana_Axe = register("solidified_mana_axe",
			p -> new Item(p.axe(ToolMaterial.DIAMOND, 6.0f, -3.0f)));
	public static final RegistryObject<Item> Solidified_Mana_Sword = register("solidified_mana_sword",
			p -> new Item(p.sword(ToolMaterial.DIAMOND, 3.0f, -2.4f)));
	public static final RegistryObject<Item> Solidified_Mana_Hoe = register("solidified_mana_hoe",
			p -> new Item(p.hoe(ToolMaterial.DIAMOND, 0.0f, 0.0f)));
	public static final RegistryObject<Item> Solidified_Mana_Shovel = register("solidified_mana_shovel",
			p -> new Item(p.shovel(ToolMaterial.DIAMOND, 1.5f, -3.0f)));

	public static final RegistryObject<Item> Steel_Pickaxe = register("steel_pickaxe",
			p -> new Item(p.pickaxe(ToolMaterial.IRON, 1.0f, -2.8f)));
	public static final RegistryObject<Item> Steel_Axe = register("steel_axe",
			p -> new Item(p.axe(ToolMaterial.IRON, 6.0f, -3.1f)));
	public static final RegistryObject<Item> Steel_Sword = register("steel_sword",
			p -> new Item(p.sword(ToolMaterial.IRON, 3.0f, -2.4f)));
	public static final RegistryObject<Item> Steel_Hoe = register("steel_hoe",
			p -> new Item(p.hoe(ToolMaterial.IRON, 0.0f, -1.0f)));
	public static final RegistryObject<Item> Steel_Shovel = register("steel_shovel",
			p -> new Item(p.shovel(ToolMaterial.IRON, 1.5f, -3.0f)));

	private static RegistryObject<Item> register(final String name, final Function<Item.Properties, Item> factory) {
		return ITEMS.register(name, () -> factory.apply(new Item.Properties().setId(ITEMS.key(name))));
	}
}
