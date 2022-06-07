package com.ml0130.techromancy.init;

import com.google.common.base.Supplier;
import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.itemdata.manatools.SolidifiedManaAxeItem;
import com.ml0130.techromancy.itemdata.manatools.SolidifiedManaHoeItem;
import com.ml0130.techromancy.itemdata.manatools.SolidifiedManaPickaxeItem;
import com.ml0130.techromancy.itemdata.manatools.SolidifiedManaShovelItem;
import com.ml0130.techromancy.itemdata.manatools.SolidifiedManaSwordItem;
import com.ml0130.techromancy.itemdata.tools.SteelAxeItem;
import com.ml0130.techromancy.itemdata.tools.SteelHoeItem;
import com.ml0130.techromancy.itemdata.tools.SteelPickaxeItem;
import com.ml0130.techromancy.itemdata.tools.SteelShovelItem;
import com.ml0130.techromancy.itemdata.tools.SteelSwordItem;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
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
	public static final RegistryObject<Item> Steel_Gear = register("steel_gear",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Item> Glass_Pipe = register("glass_pipe",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	//Ores and Ingots
	public static final RegistryObject<Item> Steel_Ingot = register("steel_ingot", 
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));

	// Tool Registry
	public static final RegistryObject<PickaxeItem> Solidified_Mana_Pickaxe = register("solidified_mana_pickaxe",
            () -> new SolidifiedManaPickaxeItem(Tiers.DIAMOND, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab))); 
	public static final RegistryObject<AxeItem> Solidified_Mana_Axe = register("solidified_mana_axe",
            () -> new SolidifiedManaAxeItem(Tiers.DIAMOND, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<SwordItem> Solidified_Mana_Sword = register("solidified_mana_sword",
            () -> new SolidifiedManaSwordItem(Tiers.DIAMOND, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<HoeItem> Solidified_Mana_Hoe = register("solidified_mana_hoe",
            () -> new SolidifiedManaHoeItem(Tiers.DIAMOND, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<ShovelItem> Solidified_Mana_Shovel = register("solidified_mana_shovel",
            () -> new SolidifiedManaShovelItem(Tiers.DIAMOND, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	public static final RegistryObject<PickaxeItem> Steel_Pickaxe = register("steel_pickaxe",
            () -> new SteelPickaxeItem(Tiers.IRON, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab))); 
	public static final RegistryObject<AxeItem> Steel_Axe = register("steel_axe",
            () -> new SteelAxeItem(Tiers.IRON, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<SwordItem> Steel_Sword = register("steel_sword",
            () -> new SteelSwordItem(Tiers.IRON, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<HoeItem> Steel_Hoe = register("steel_hoe",
            () -> new SteelHoeItem(Tiers.IRON, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<ShovelItem> Steel_Shovel = register("steel_shovel",
            () -> new SteelShovelItem(Tiers.IRON, 0, 0, new Item.Properties().tab(Techromancy.Techromancy_Tab)));

	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
		return ITEMS.register(name, item);
	}
}