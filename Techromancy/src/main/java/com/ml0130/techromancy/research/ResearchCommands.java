package com.ml0130.techromancy.research;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;

/**
 * Commands for sharing research between players:
 * <ul>
 *   <li>{@code /techromancy research invite <player>} - offer to share your research</li>
 *   <li>{@code /techromancy research accept <player>} - accept an offer (merges your teams)</li>
 *   <li>{@code /techromancy research decline <player>} - decline an offer</li>
 *   <li>{@code /techromancy research leave} - split off into a solo team (keeps what you know)</li>
 *   <li>{@code /techromancy research status} - show your team size and knowledge counts</li>
 * </ul>
 * Pending invites are kept in memory only (cleared on server restart).
 */
public final class ResearchCommands {

	/** target player -> the player who invited them. */
	private static final Map<UUID, UUID> PENDING = new ConcurrentHashMap<>();

	private ResearchCommands() {
	}

	/** Register via {@code RegisterCommandsEvent.BUS.addListener(ResearchCommands::onRegisterCommands)}. */
	public static void onRegisterCommands(RegisterCommandsEvent event) {
		register(event.getDispatcher());
	}

	private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("techromancy")
				.then(Commands.literal("research")
						.then(Commands.literal("invite")
								.then(Commands.argument("player", EntityArgument.player())
										.executes(ResearchCommands::invite)))
						.then(Commands.literal("accept")
								.then(Commands.argument("player", EntityArgument.player())
										.executes(ResearchCommands::accept)))
						.then(Commands.literal("decline")
								.then(Commands.argument("player", EntityArgument.player())
										.executes(ResearchCommands::decline)))
						.then(Commands.literal("leave")
								.executes(ResearchCommands::leave))
						.then(Commands.literal("status")
								.executes(ResearchCommands::status))));
	}

	private static int invite(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer inviter = ctx.getSource().getPlayerOrException();
		ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
		if (target.getUUID().equals(inviter.getUUID())) {
			ctx.getSource().sendFailure(Component.literal("You can't invite yourself."));
			return 0;
		}
		PENDING.put(target.getUUID(), inviter.getUUID());
		String inviterName = inviter.getName().getString();
		ctx.getSource().sendSuccess(
				() -> Component.literal("Invited " + target.getName().getString() + " to share research."), false);
		target.sendSystemMessage(Component.literal(inviterName
				+ " invited you to share research. Use: /techromancy research accept " + inviterName));
		return 1;
	}

	private static int accept(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer accepter = ctx.getSource().getPlayerOrException();
		ServerPlayer inviter = EntityArgument.getPlayer(ctx, "player");
		UUID pending = PENDING.get(accepter.getUUID());
		if (pending == null || !pending.equals(inviter.getUUID())) {
			ctx.getSource().sendFailure(
					Component.literal("No pending research invite from " + inviter.getName().getString() + "."));
			return 0;
		}
		PENDING.remove(accepter.getUUID());
		ResearchSavedData.get(accepter.getServer()).share(inviter.getUUID(), accepter.getUUID());
		ctx.getSource().sendSuccess(
				() -> Component.literal("You now share research with " + inviter.getName().getString() + "."), false);
		inviter.sendSystemMessage(
				Component.literal(accepter.getName().getString() + " accepted your research invite."));
		return 1;
	}

	private static int decline(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer accepter = ctx.getSource().getPlayerOrException();
		ServerPlayer inviter = EntityArgument.getPlayer(ctx, "player");
		PENDING.remove(accepter.getUUID(), inviter.getUUID());
		ctx.getSource().sendSuccess(() -> Component.literal("Declined."), false);
		return 1;
	}

	private static int leave(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer player = ctx.getSource().getPlayerOrException();
		ResearchSavedData.get(player.getServer()).leave(player.getUUID());
		ctx.getSource().sendSuccess(
				() -> Component.literal("You left your research team (you kept everything you already knew)."), false);
		return 1;
	}

	private static int status(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer player = ctx.getSource().getPlayerOrException();
		ResearchSavedData data = ResearchSavedData.get(player.getServer());
		int size = data.teamSize(player);
		int identified = data.identifiedCount(player);
		int researched = data.researchedCount(player);
		ctx.getSource().sendSuccess(() -> Component.literal("Research team: " + size + " member(s) · identified "
				+ identified + " · researched " + researched), false);
		return 1;
	}
}
