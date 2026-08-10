package com.ml0130.techromancy.block.reserch;

import com.ml0130.techromancy.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * The Discovery Table: core of a tiered crafting multiblock. The bigger/better the structure built around
 * it, the higher its tier - and higher tiers can craft better things. Right-click (empty hand) to check
 * the current tier.
 *
 * <p>This establishes the structure + tier detection; the tier-gated crafting itself is the next step.
 *
 * <p><b>Tier 2 structure:</b> a 3x3 Steel Block platform directly below the table and its 8 neighbours,
 * plus a {@link ResearchPillar} on each of the four corners at the table's own level.
 */
public class DiscoveryTable extends Block {

	public DiscoveryTable(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
			BlockHitResult hit) {
		if (!level.isClientSide()) {
			int tier = tierAt(level, pos);
			String msg = "Discovery Table — Tier " + tier;
			if (tier < 2) {
				msg += " (for Tier 2: a 3×3 Steel Block platform beneath it + a Research Pillar on each corner)";
			}
			player.displayClientMessage(Component.literal(msg), false);
		}
		return InteractionResult.SUCCESS;
	}

	/** The structure tier of a Discovery Table at {@code pos}. Tier 1 is the bare table. */
	public static int tierAt(Level level, BlockPos pos) {
		return hasTier2Structure(level, pos) ? 2 : 1;
	}

	private static boolean hasTier2Structure(Level level, BlockPos pos) {
		// 3x3 Steel Block platform one block below the table and its 8 neighbours.
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				if (!level.getBlockState(pos.offset(dx, -1, dz)).is(BlockInit.Steel_Block.get())) {
					return false;
				}
			}
		}
		// A Research Pillar on each corner, at the table's level.
		BlockPos[] corners = { pos.offset(1, 0, 1), pos.offset(1, 0, -1), pos.offset(-1, 0, 1), pos.offset(-1, 0, -1) };
		for (BlockPos corner : corners) {
			if (!level.getBlockState(corner).is(BlockInit.Research_Pillar.get())) {
				return false;
			}
		}
		return true;
	}
}
