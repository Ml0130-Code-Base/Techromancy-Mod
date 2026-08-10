package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedSteamEngineBlockEntity extends AbstractSteamEngineBlockEntity {

	public static final int CAPACITY = 400_000;
	public static final int MAX_OUTPUT = 1_600;
	public static final int GENERATION = 120;

	public AdvancedSteamEngineBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.ADVANCED_STEAM_ENGINE.get(), pos, state, CAPACITY, MAX_OUTPUT, GENERATION);
	}
}
