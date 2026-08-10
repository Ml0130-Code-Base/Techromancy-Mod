package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedSteamEngine extends AbstractSteamEngineBlock {

	public AdvancedSteamEngine(Properties properties) {
		super(properties);
	}

	@Override
	protected BlockEntityType<? extends AbstractSteamEngineBlockEntity> getBlockEntityType() {
		return BlockEntityInit.ADVANCED_STEAM_ENGINE.get();
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AdvancedSteamEngineBlockEntity(pos, state);
	}
}
