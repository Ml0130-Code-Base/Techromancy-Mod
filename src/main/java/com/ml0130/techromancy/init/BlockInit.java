package com.ml0130.techromancy.init;

import java.util.function.Function;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.block.magic.HexGate;
import com.ml0130.techromancy.block.magic.MysticSteamEngine;
import com.ml0130.techromancy.block.reserch.DiscoveryTable;
import com.ml0130.techromancy.block.reserch.EssenceStripper;
import com.ml0130.techromancy.block.reserch.ResearchPillar;
import com.ml0130.techromancy.block.steam.AdvancedSteamEngine;
import com.ml0130.techromancy.block.steam.Compressor;
import com.ml0130.techromancy.block.steam.PressurizedGlassPipe;
import com.ml0130.techromancy.block.steam.Smelter;
import com.ml0130.techromancy.block.steam.WoodenSteamEngine;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Techromancy.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;

	// Magic Blocks
	public static final RegistryObject<Block> Hex_Gate = register("hex_gate", HexGate::new,
			BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(3.0f));
	public static final RegistryObject<Block> Mystic_Steam_Engine = register("mystic_steam_engine",
			MysticSteamEngine::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(3.0f));

	// Steam Blocks
	public static final RegistryObject<Block> Wooden_Steam_Engine = register("wooden_steam_engine",
			WoodenSteamEngine::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(3.0f));
	public static final RegistryObject<Block> Advanced_Steam_Engine = register("advanced_steam_engine",
			AdvancedSteamEngine::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(3.0f));
	public static final RegistryObject<Block> Compressor = register("compressor", Compressor::new,
			BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(3.0f));
	public static final RegistryObject<Block> Smelter = register("smelter", Smelter::new,
			BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(3.5f));
	public static final RegistryObject<Block> Pressurized_Glass_Pipe = register("pressurized_glass_pipe",
			PressurizedGlassPipe::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(3.0f));

	// Other Blocks
	public static final RegistryObject<Block> Steel_Block = register("steel_block", Block::new,
			BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0f));

	// Research Blocks
	public static final RegistryObject<Block> Discovery_Table = register("discovery_table", DiscoveryTable::new,
			BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2.0f));
	public static final RegistryObject<Block> Essence_Stripper = register("essence_striper", EssenceStripper::new,
			BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(5.0f));
	public static final RegistryObject<Block> Research_Pillar = register("research_pillar", ResearchPillar::new,
			BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(3.0f));

	private static <T extends Block> RegistryObject<T> register(final String name,
			final Function<BlockBehaviour.Properties, ? extends T> factory, final BlockBehaviour.Properties props) {
		RegistryObject<T> obj = BLOCKS.register(name, () -> factory.apply(props.setId(BLOCKS.key(name))));
		ITEMS.register(name, () -> new BlockItem(obj.get(), new Item.Properties().setId(ITEMS.key(name))));
		return obj;
	}
}
