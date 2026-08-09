package com.ml0130.techromancy.research;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.UUIDUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/**
 * World-level store of all research knowledge, organised into <b>teams</b> so it can be shared:
 * <ul>
 *   <li>{@code teams} - each team's shared {@link TeamKnowledge};</li>
 *   <li>{@code playerTeams} - which team each player belongs to. A player with no entry is treated as a
 *       solo team whose id equals their own UUID.</li>
 * </ul>
 * Solo play is just a team of one; {@link #share} merges two teams together. Kept on the overworld's
 * data storage so it is global across dimensions and server-authoritative.
 */
public class ResearchSavedData extends SavedData {

	public static final Codec<ResearchSavedData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.unboundedMap(UUIDUtil.STRING_CODEC, TeamKnowledge.CODEC).fieldOf("teams")
					.forGetter(d -> d.teams),
			Codec.unboundedMap(UUIDUtil.STRING_CODEC, UUIDUtil.STRING_CODEC).fieldOf("playerTeams")
					.forGetter(d -> d.playerTeams))
			.apply(inst, ResearchSavedData::new));

	public static final SavedDataType<ResearchSavedData> TYPE = new SavedDataType<>("techromancy_research",
			ResearchSavedData::new, CODEC, DataFixTypes.LEVEL);

	private final Map<UUID, TeamKnowledge> teams;
	private final Map<UUID, UUID> playerTeams;

	public ResearchSavedData() {
		this(Map.of(), Map.of());
	}

	private ResearchSavedData(Map<UUID, TeamKnowledge> teams, Map<UUID, UUID> playerTeams) {
		this.teams = new HashMap<>(teams);
		this.playerTeams = new HashMap<>(playerTeams);
	}

	/** The single global research store (lives on the overworld). */
	public static ResearchSavedData get(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(TYPE);
	}

	// --- team resolution -----------------------------------------------------

	private UUID teamId(UUID player) {
		return playerTeams.computeIfAbsent(player, p -> p);
	}

	/** The knowledge of a player's team, creating an empty solo team on first use. */
	private TeamKnowledge team(UUID player) {
		return teams.computeIfAbsent(teamId(player), t -> new TeamKnowledge());
	}

	// --- queries -------------------------------------------------------------

	public boolean hasIdentified(Player player, String id) {
		return team(player.getUUID()).hasIdentified(id);
	}

	public boolean hasResearched(Player player, String id) {
		return team(player.getUUID()).hasResearched(id);
	}

	public boolean hasComposition(Player player, String id) {
		return team(player.getUUID()).hasComposition(id);
	}

	/** Number of players currently sharing this player's team (at least 1). */
	public int teamSize(Player player) {
		UUID tid = teamId(player.getUUID());
		int n = 0;
		for (UUID t : playerTeams.values()) {
			if (t.equals(tid)) {
				n++;
			}
		}
		return Math.max(1, n);
	}

	public int identifiedCount(Player player) {
		return team(player.getUUID()).identifiedCount();
	}

	public int researchedCount(Player player) {
		return team(player.getUUID()).researchedCount();
	}

	/** True if this player's team meets the entry's unlock requirement. */
	public boolean isUnlocked(Player player, ResearchEntry entry) {
		TeamKnowledge k = team(player.getUUID());
		return switch (entry.requirement()) {
			case ResearchRequirement.None ignored -> true;
			case ResearchRequirement.Scanned s -> k.hasIdentified(s.targetId());
			case ResearchRequirement.Researched r -> k.hasResearched(r.researchId());
		};
	}

	// --- mutations -----------------------------------------------------------

	/** Records that the player's team has identified {@code id}. @return true if newly learned. */
	public boolean identify(Player player, String id) {
		if (team(player.getUUID()).addIdentified(id)) {
			setDirty();
			return true;
		}
		return false;
	}

	/** Records that the player's team has completed research {@code id}. @return true if newly learned. */
	public boolean completeResearch(Player player, String id) {
		if (team(player.getUUID()).addResearch(id)) {
			setDirty();
			return true;
		}
		return false;
	}

	/** Records that the player's team now knows the full composition of {@code id}. @return true if newly learned. */
	public boolean revealComposition(Player player, String id) {
		if (team(player.getUUID()).addComposition(id)) {
			setDirty();
			return true;
		}
		return false;
	}

	// --- sharing -------------------------------------------------------------

	/** Moves {@code joining} (and anyone on their team) onto {@code host}'s team, merging their knowledge. */
	public void share(UUID host, UUID joining) {
		UUID hostTeam = teamId(host);
		UUID joinTeam = teamId(joining);
		if (hostTeam.equals(joinTeam)) {
			return;
		}
		teams.computeIfAbsent(hostTeam, t -> new TeamKnowledge())
				.mergeFrom(teams.computeIfAbsent(joinTeam, t -> new TeamKnowledge()));
		for (Map.Entry<UUID, UUID> e : playerTeams.entrySet()) {
			if (e.getValue().equals(joinTeam)) {
				e.setValue(hostTeam);
			}
		}
		teams.remove(joinTeam);
		setDirty();
	}

	/** Splits {@code player} back into their own team, keeping a copy of everything they currently know. */
	public void leave(UUID player) {
		TeamKnowledge kept = team(player).copy();
		UUID solo = UUID.randomUUID();
		teams.put(solo, kept);
		playerTeams.put(player, solo);
		setDirty();
	}
}
