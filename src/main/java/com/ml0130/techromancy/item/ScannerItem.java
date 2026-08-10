package com.ml0130.techromancy.item;

import java.util.List;

import com.ml0130.techromancy.essence.EssenceEntry;
import com.ml0130.techromancy.essence.EssenceProfile;
import com.ml0130.techromancy.essence.EssenceResolver;
import com.ml0130.techromancy.research.ResearchSavedData;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

/**
 * The Scanner: right-click a block to identify it, or use it with an item held in your other hand to
 * identify that item. Identifying reveals the "surface" essences; deeper ones need the Essence Striper.
 */
public class ScannerItem extends Item {

	public ScannerItem(Properties properties) {
		super(properties);
	}

	// Right-click a block -> identify the block.
	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Player player = ctx.getPlayer();
		if (player == null) {
			return InteractionResult.PASS;
		}
		if (!ctx.getLevel().isClientSide()) {
			Block block = ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock();
			scan(player, BuiltInRegistries.BLOCK.getKey(block).toString());
		}
		return InteractionResult.SUCCESS;
	}

	// Right-click the air -> identify the item held in the OTHER hand, if any.
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		InteractionHand other = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
		ItemStack target = player.getItemInHand(other);
		if (target.isEmpty() || target.getItem() == this) {
			if (!level.isClientSide()) {
				player.displayClientMessage(Component.literal(
						"Point the Scanner at a block, or hold an item in your other hand to scan it."), true);
			}
			return InteractionResult.PASS;
		}
		if (!level.isClientSide()) {
			scan(player, BuiltInRegistries.ITEM.getKey(target.getItem()).toString());
		}
		return InteractionResult.SUCCESS;
	}

	private static void scan(Player player, String id) {
		ResearchSavedData data = ResearchSavedData.get(player);
		boolean isNew = data.identify(player, id);
		EssenceProfile profile = EssenceResolver.resolve(id);
		List<EssenceEntry> surface = profile.surface();

		StringBuilder msg = new StringBuilder(isNew ? "Identified " : "Already known: ").append(id);
		if (!surface.isEmpty()) {
			msg.append(" — ").append(format(surface));
		} else if (profile.isEmpty()) {
			msg.append(" — no essences catalogued yet");
		}
		if (profile.needsStripForFull()) {
			msg.append(" (strip it for the full breakdown)");
		}
		player.displayClientMessage(Component.literal(msg.toString()), false);
	}

	private static String format(List<EssenceEntry> entries) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < entries.size(); i++) {
			EssenceEntry e = entries.get(i);
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(e.essence().display()).append(" x").append(e.amount());
		}
		return sb.toString();
	}
}
