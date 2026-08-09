package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.phys.BlockHitResult;

/**
 * A basic FE-powered smelter. Until it has a GUI: right-click with an item to load the input slot,
 * right-click empty-handed to collect the output and read its power/status. It also exposes item + energy
 * capabilities, so hoppers/pipes can automate it.
 */
public class Smelter extends Block implements EntityBlock {

	public Smelter(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SmelterBlockEntity(pos, state);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return null;
		}
		return type == BlockEntityInit.SMELTER.get()
				? (BlockEntityTicker<T>) (BlockEntityTicker<SmelterBlockEntity>) SmelterBlockEntity::serverTick
				: null;
	}

	// Right-click with an item -> load it into the input slot.
	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide()) {
			if (level.getBlockEntity(pos) instanceof SmelterBlockEntity smelter) {
				ItemStack remainder = smelter.getItems().insertItem(0, stack.copy(), false);
				int inserted = stack.getCount() - remainder.getCount();
				if (inserted > 0) {
					stack.shrink(inserted);
					player.displayClientMessage(Component.literal("Loaded " + inserted + " to smelt."), true);
				} else {
					player.displayClientMessage(Component.literal("Input is full or holds a different item."), true);
				}
			}
		}
		return InteractionResult.SUCCESS;
	}

	// Right-click empty-handed -> collect the output and report status.
	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
			BlockHitResult hit) {
		if (!level.isClientSide()) {
			if (level.getBlockEntity(pos) instanceof SmelterBlockEntity smelter) {
				ItemStack out = smelter.getItems().extractItem(1, 64, false);
				if (!out.isEmpty() && !player.addItem(out)) {
					player.drop(out, false);
				}
				player.displayClientMessage(Component.literal("Smelter: " + smelter.getEnergyStored() + " FE"
						+ (smelter.isWorking() ? " · smelting…" : " · idle")), false);
			}
		}
		return InteractionResult.SUCCESS;
	}
}
