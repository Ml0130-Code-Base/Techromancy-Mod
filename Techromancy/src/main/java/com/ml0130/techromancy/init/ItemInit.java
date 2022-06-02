package com.ml0130.techromancy.init;

import com.google.common.base.Supplier;
import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.itemdata.tools.solidifed_mana_hoe;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Techromancy.MOD_ID);

	public static final RegistryObject<Item> Solidified_Mana = register("solidified_mana",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	//Tool Registry
	public static final RegistryObject<Item> Solidified_Mana_Pickaxe = register("solidified_mana_pickaxe",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Item> Solidified_Mana_Axe = register("solidified_mana_axe",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Item> Solidified_Mana_Sward = register("solidified_mana_sward",
			() -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Item> Solidified_Mana_Hoe = register("solidified_mana_hoe",
			() -> new solidifed_mana_hoe(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item){
		return ITEMS.register(name, item);
	}
}
