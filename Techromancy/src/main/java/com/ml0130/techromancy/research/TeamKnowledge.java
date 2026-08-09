package com.ml0130.techromancy.research;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * The research a single team knows: the set of things it has {@code identified} (scanned) and the set of
 * research entries it has {@code completed}. Shared by every player on the team.
 */
public class TeamKnowledge {

	public static final Codec<TeamKnowledge> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.STRING.listOf().fieldOf("identified").forGetter(k -> List.copyOf(k.identified)),
			Codec.STRING.listOf().fieldOf("research").forGetter(k -> List.copyOf(k.completedResearch)))
			.apply(inst, TeamKnowledge::new));

	private final Set<String> identified;
	private final Set<String> completedResearch;

	public TeamKnowledge() {
		this(List.of(), List.of());
	}

	private TeamKnowledge(List<String> identified, List<String> completedResearch) {
		this.identified = new HashSet<>(identified);
		this.completedResearch = new HashSet<>(completedResearch);
	}

	public boolean hasIdentified(String id) {
		return identified.contains(id);
	}

	public boolean hasResearched(String id) {
		return completedResearch.contains(id);
	}

	/** @return true if this was newly added (changed the set). */
	public boolean addIdentified(String id) {
		return identified.add(id);
	}

	/** @return true if this was newly added (changed the set). */
	public boolean addResearch(String id) {
		return completedResearch.add(id);
	}

	public int identifiedCount() {
		return identified.size();
	}

	public int researchedCount() {
		return completedResearch.size();
	}

	/** Folds another team's knowledge into this one (used when two teams merge). */
	public void mergeFrom(TeamKnowledge other) {
		this.identified.addAll(other.identified);
		this.completedResearch.addAll(other.completedResearch);
	}

	public TeamKnowledge copy() {
		TeamKnowledge c = new TeamKnowledge();
		c.identified.addAll(this.identified);
		c.completedResearch.addAll(this.completedResearch);
		return c;
	}
}
