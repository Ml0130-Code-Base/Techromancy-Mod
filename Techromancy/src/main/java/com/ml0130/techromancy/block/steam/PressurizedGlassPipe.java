package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The Glass Pipe: a transport conduit. Currently carries Forge Energy (see
 * {@link PressurizedGlassPipeBlockEntity}); item transport is a planned second pass on the same block.
 */
public class PressurizedGlassPipe extends Block implements EntityBlock {

	public PressurizedGlassPipe(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PressurizedGlassPipeBlockEntity(pos, state);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return null;
		}
		return type == BlockEntityInit.PRESSURIZED_GLASS_PIPE.get()
				? (BlockEntityTicker<T>) (BlockEntityTicker<PressurizedGlassPipeBlockEntity>) PressurizedGlassPipeBlockEntity::serverTick
				: null;
	}
}
