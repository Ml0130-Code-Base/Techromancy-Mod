package com.ml0130.techromancy.init;

import java.util.Set;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.block.magic.MysticSteamEngineBlockEntity;
import com.ml0130.techromancy.block.steam.AdvancedSteamEngineBlockEntity;
import com.ml0130.techromancy.block.steam.WoodenSteamEngineBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Techromancy.MOD_ID);

	public static final RegistryObject<BlockEntityType<WoodenSteamEngineBlockEntity>> WOODEN_STEAM_ENGINE = BLOCK_ENTITIES
			.register("wooden_steam_engine", () -> new BlockEntityType<>(WoodenSteamEngineBlockEntity::new,
					Set.of(BlockInit.Wooden_Steam_Engine.get())));

	public static final RegistryObject<BlockEntityType<AdvancedSteamEngineBlockEntity>> ADVANCED_STEAM_ENGINE = BLOCK_ENTITIES
			.register("advanced_steam_engine", () -> new BlockEntityType<>(AdvancedSteamEngineBlockEntity::new,
					Set.of(BlockInit.Advanced_Steam_Engine.get())));

	public static final RegistryObject<BlockEntityType<MysticSteamEngineBlockEntity>> MYSTIC_STEAM_ENGINE = BLOCK_ENTITIES
			.register("mystic_steam_engine", () -> new BlockEntityType<>(MysticSteamEngineBlockEntity::new,
					Set.of(BlockInit.Mystic_Steam_Engine.get())));
}
