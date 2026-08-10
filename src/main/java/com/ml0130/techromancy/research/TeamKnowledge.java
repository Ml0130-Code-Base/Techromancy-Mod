package com.ml0130.techromancy.research;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * The research a single team knows, shared by every player on the team:
 * <ul>
 *   <li>{@code identified} - things it has scanned ("what is it?");</li>
 *   <li>{@code compositionKnown} - things it has stripped ("what's it made of?");</li>
 *   <li>{@code completedResearch} - research entries it has finished.</li>
 * </ul>
 */
public class TeamKnowledge {

	public static final Codec<TeamKnowledge> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.STRING.listOf().fieldOf("identified").forGetter(k -> List.copyOf(k.identified)),
			Codec.STRING.listOf().fieldOf("research").forGetter(k -> List.copyOf(k.completedResearch)),
			Codec.STRING.listOf().fieldOf("composition").forGetter(k -> List.copyOf(k.compositionKnown)))
			.apply(inst, TeamKnowledge::new));

	private final Set<String> identified;
	private final Set<String> completedResearch;
	private final Set<String> compositionKnown;

	public TeamKnowledge() {
		this(List.of(), List.of(), List.of());
	}

	private TeamKnowledge(List<String> identified, List<String> completedResearch, List<String> compositionKnown) {
		this.identified = new HashSet<>(identified);
		this.completedResearch = new HashSet<>(completedResearch);
		this.compositionKnown = new HashSet<>(compositionKnown);
	}

	public boolean hasIdentified(String id) {
		return identified.contains(id);
	}

	public boolean hasResearched(String id) {
		return completedResearch.contains(id);
	}

	public boolean hasComposition(String id) {
		return compositionKnown.contains(id);
	}

	/** @return true if newly added. */
	public boolean addIdentified(String id) {
		return identified.add(id);
	}

	/** @return true if newly added. */
	public boolean addResearch(String id) {
		return completedResearch.add(id);
	}

	/** @return true if newly added. */
	public boolean addComposition(String id) {
		return compositionKnown.add(id);
	}

	public int identifiedCount() {
		return identified.size();
	}

	public int researchedCount() {
		return completedResearch.size();
	}

	public int compositionCount() {
		return compositionKnown.size();
	}

	/** Folds another team's knowledge into this one (used when two teams merge). */
	public void mergeFrom(TeamKnowledge other) {
		this.identified.addAll(other.identified);
		this.completedResearch.addAll(other.completedResearch);
		this.compositionKnown.addAll(other.compositionKnown);
	}

	public TeamKnowledge copy() {
		TeamKnowledge c = new TeamKnowledge();
		c.identified.addAll(this.identified);
		c.completedResearch.addAll(this.completedResearch);
		c.compositionKnown.addAll(this.compositionKnown);
		return c;
	}
}
