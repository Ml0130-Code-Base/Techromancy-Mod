package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WoodenSteamEngineBlockEntity extends AbstractSteamEngineBlockEntity {

	public static final int CAPACITY = 100_000;
	public static final int MAX_OUTPUT = 400;
	public static final int GENERATION = 40;

	public WoodenSteamEngineBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.WOODEN_STEAM_ENGINE.get(), pos, state, CAPACITY, MAX_OUTPUT, GENERATION);
	}
}
