package com.ml0130.techromancy.init;

import com.google.common.base.Supplier;
import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.itemdata.tools.SolidifiedManaAxeItem;
import com.ml0130.techromancy.itemdata.tools.SolidifiedManaHoeItem;
import com.ml0130.techromancy.itemdata.tools.SolidifiedManaPickaxeItem;
import com.ml0130.techromancy.itemdata.tools.SolidifiedManaShovelItem;
import com.ml0130.techromancy.itemdata.tools.SolidifiedManaSwordItem;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			Techromancy.MOD_ID);
	// Magic Items
	public static final RegistryObject<Item> Solidified_Mana = register("solidified_mana",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Item> Imbued_Gear = register("imbued_gear",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));

	// Steam Items
	public static final RegistryObject<Item> Wooden_Gear = register("wooden_gear",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Item> Metal_Gear = register("metal_gear",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Item> Glass_Pipe = register("glass_pipe",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));

	// Tool Registry
	public static final RegistryObject<PickaxeItem> Solidified_Mana_Pickaxe = register("solidified_mana_pickaxe",
            () -> new SolidifiedManaPickaxeItem(null, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab))); 
	public static final RegistryObject<AxeItem> Solidified_Mana_Axe = register("solidified_mana_axe",
            () -> new SolidifiedManaAxeItem(null, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<SwordItem> Solidified_Mana_Sword = register("solidified_mana_sword",
            () -> new SolidifiedManaSwordItem(null, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<HoeItem> Solidified_Mana_Hoe = register("solidified_mana_hoe",
            () -> new SolidifiedManaHoeItem(null, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<ShovelItem> Solidified_Mana_Shovel = register("solidified_mana_shovel",
            () -> new SolidifiedManaShovelItem(null, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));

	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
		return ITEMS.register(name, item);
	}
}