package com.ml0130.techromancy.init;

import java.util.function.Function;

import com.google.common.base.Supplier;
import com.ml0130.techromancy.Techromancy;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Techromancy.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;
	
	//Magic Blocks
	public static final RegistryObject<Block> Hex_Gate = register("hex_gate", 
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Mystic_Steam_Engine = register("mystic_steam_engine", 
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_MAGENTA)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	//Steam Blocks
	public static final RegistryObject<Block> Wooden_Steam_Engine = register("wooden_steam_engine", 
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_BROWN)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Advanced_Steam_Engine = register("advanced_steam_engine", 
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Compressor = register("compressor", 
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Pressurized_Glass_Pipe = register("pressurized_glass_pipe", 
			() -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));

	
	private static <T extends Block> RegistryObject<T> registerBlock(final String name,
			final Supplier<? extends T> block) {
		return BLOCKS.register(name, block);
	}

	private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block,
			Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> obj = registerBlock(name, block);
		ITEMS.register(name, item.apply(obj));
		return obj;
	}
	
}
