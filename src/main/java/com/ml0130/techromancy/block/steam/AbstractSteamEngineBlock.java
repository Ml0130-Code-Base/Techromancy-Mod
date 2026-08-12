package com.ml0130.techromancy.block.steam;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Shared block behaviour for every steam engine tier: a {@code lit} blockstate, right-click-with-fuel to
 * load the engine, and right-click-empty-handed to read its stored energy. Concrete tiers supply their
 * block-entity type (for ticking) and construct their block entity.
 */
public abstract class AbstractSteamEngineBlock extends Block implements EntityBlock {

	protected AbstractSteamEngineBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(BlockStateProperties.LIT, false));
	}

	/** The block-entity type for this tier, used to match the server ticker. */
	protected abstract BlockEntityType<? extends AbstractSteamEngineBlockEntity> getBlockEntityType();

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.LIT);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return null;
		}
		return type == getBlockEntityType()
				? (BlockEntityTicker<T>) (BlockEntityTicker<AbstractSteamEngineBlockEntity>) AbstractSteamEngineBlockEntity::serverTick
				: null;
	}

	// Right-click (with or without an item) opens the engine's GUI; sneak to allow normal block placement.
	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (player.isSecondaryUseActive()) {
			return InteractionResult.PASS;
		}
		return openMenu(level, pos, player);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
			BlockHitResult hit) {
		return openMenu(level, pos, player);
	}

	private static InteractionResult openMenu(Level level, BlockPos pos, Player player) {
		if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer
				&& level.getBlockEntity(pos) instanceof AbstractSteamEngineBlockEntity engine) {
			serverPlayer.openMenu(engine, buf -> buf.writeBlockPos(pos));
		}
		return InteractionResult.SUCCESS;
	}
}
