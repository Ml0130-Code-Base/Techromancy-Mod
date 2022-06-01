package com.ml0130.techromancy.init;

import com.google.common.base.Supplier;
import com.ml0130.techromancy.Techromancy;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Techromancy.MOD_ID);

	//public static final RegistryObject<Item> Hex_Gate = register("hex_gate", () -> new Item(new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item){
		return ITEMS.register(name, item);
	}
}
