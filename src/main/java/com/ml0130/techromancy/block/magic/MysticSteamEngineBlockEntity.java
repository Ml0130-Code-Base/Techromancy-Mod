package com.ml0130.techromancy.block.magic;

import com.ml0130.techromancy.block.steam.AbstractSteamEngineBlockEntity;
import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MysticSteamEngineBlockEntity extends AbstractSteamEngineBlockEntity {

	public static final int CAPACITY = 1_000_000;
	public static final int MAX_OUTPUT = 4_000;
	public static final int GENERATION = 320;

	public MysticSteamEngineBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.MYSTIC_STEAM_ENGINE.get(), pos, state, CAPACITY, MAX_OUTPUT, GENERATION);
	}
}
