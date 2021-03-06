package com.ml0130.techromancy.init;

import java.util.function.Function;

import com.google.common.base.Supplier;
import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.block.magic.HexGate;
import com.ml0130.techromancy.block.magic.MysticSteamEngine;
import com.ml0130.techromancy.block.reserch.DiscoveryTable;
import com.ml0130.techromancy.block.reserch.EssenceStriper;
import com.ml0130.techromancy.block.steam.AdvancedSteamEngine;
import com.ml0130.techromancy.block.steam.Compressor;
import com.ml0130.techromancy.block.steam.PressurizedGlassPipe;
import com.ml0130.techromancy.block.steam.WoodenSteamEngine;

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
			() -> new HexGate(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_CYAN)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Mystic_Steam_Engine = register("mystic_steam_engine", 
			() -> new MysticSteamEngine(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_MAGENTA)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	//Steam Blocks
	public static final RegistryObject<Block> Wooden_Steam_Engine = register("wooden_steam_engine", 
			() -> new WoodenSteamEngine(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_BROWN)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Advanced_Steam_Engine = register("advanced_steam_engine", 
			() -> new AdvancedSteamEngine(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Compressor = register("compressor", 
			() -> new Compressor(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Pressurized_Glass_Pipe = register("pressurized_glass_pipe", 
			() -> new PressurizedGlassPipe(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_WHITE)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	//Other Blocks
	public static final RegistryObject<Block> Steel_Block = register("steel_block", 
			() -> new PressurizedGlassPipe(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_WHITE)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	
	//Reserch Blocks
	public static final RegistryObject<Block> Discovery_Table = register("discovery_table", 
			() -> new DiscoveryTable(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN)), 
			object -> () -> new BlockItem(object.get(),new Item.Properties().tab(Techromancy.Techromancy_Tab)));
	public static final RegistryObject<Block> Essence_Striper = register("essence_striper", 
			() -> new EssenceStriper(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.TERRACOTTA_WHITE)), 
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
