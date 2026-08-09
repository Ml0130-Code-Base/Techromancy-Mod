package com.ml0130.techromancy.block.reserch;

import java.util.List;

import com.ml0130.techromancy.essence.EssenceEntry;
import com.ml0130.techromancy.essence.EssenceProfile;
import com.ml0130.techromancy.essence.EssenceResolver;
import com.ml0130.techromancy.research.ResearchSavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Essence Striper: right-click it holding an item to reveal what the item is made of. The item must be
 * {@linkplain ResearchSavedData#hasIdentified identified} first (scan it). Stripping reveals the FULL
 * breakdown, including "deep" essences that scanning alone cannot show. (For now it only reveals - a
 * later pass turns it into a machine that consumes the item and draws power.)
 */
public class EssenceStriper extends Block {

	public EssenceStriper(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (stack.isEmpty()) {
			if (!level.isClientSide()) {
				player.displayClientMessage(Component.literal("Hold an item to strip its essences."), true);
			}
			return InteractionResult.PASS;
		}
		if (!level.isClientSide()) {
			String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
			ResearchSavedData data = ResearchSavedData.get(player.getServer());
			if (!data.hasIdentified(player, id)) {
				player.displayClientMessage(Component.literal("Scan this with a Scanner first to identify it."), true);
				return InteractionResult.SUCCESS;
			}
			data.revealComposition(player, id);
			report(player, id, EssenceResolver.resolve(id));
		}
		return InteractionResult.SUCCESS;
	}

	private static void report(Player player, String id, EssenceProfile profile) {
		List<EssenceEntry> full = profile.full();
		if (full.isEmpty()) {
			player.displayClientMessage(Component.literal(id + " — no essences catalogued yet"), false);
			return;
		}
		StringBuilder msg = new StringBuilder(id).append(" — full breakdown: ");
		for (int i = 0; i < full.size(); i++) {
			EssenceEntry e = full.get(i);
			if (i > 0) {
				msg.append(", ");
			}
			msg.append(e.essence().display()).append(" x").append(e.amount());
			if (e.requiresStrip()) {
				msg.append("*");
			}
		}
		msg.append("   (* revealed only by stripping)");
		player.displayClientMessage(Component.literal(msg.toString()), false);
	}
}
