package com.ml0130.techromancy.block.magic;

import com.ml0130.techromancy.block.steam.AbstractSteamEngineBlock;
import com.ml0130.techromancy.block.steam.AbstractSteamEngineBlockEntity;
import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MysticSteamEngine extends AbstractSteamEngineBlock {

	public MysticSteamEngine(Properties properties) {
		super(properties);
	}

	@Override
	protected BlockEntityType<? extends AbstractSteamEngineBlockEntity> getBlockEntityType() {
		return BlockEntityInit.MYSTIC_STEAM_ENGINE.get();
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MysticSteamEngineBlockEntity(pos, state);
	}
}
