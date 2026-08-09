package com.ml0130.techromancy.research;

/**
 * The initial selection of research entries - the content list. Add new topics here.
 *
 * <p>Requirement kinds map onto the design's access tiers:
 * <ul>
 *   <li>{@link ResearchRequirement.Scanned} - scan-unlocked (identify a thing and it opens up);</li>
 *   <li>{@link ResearchRequirement.Researched} - research-gated (needs an earlier entry done first);</li>
 *   <li>{@link ResearchRequirement.None} - available from the start.</li>
 * </ul>
 *
 * {@link #init()} is called during mod construction so this class (and its registrations) load.
 */
public final class ModResearch {

	// Scan-unlocked: identifying steel opens the steel branch.
	public static final ResearchEntry STEEL = ResearchRegistry.register(new ResearchEntry(
			"steel",
			"research.techromancy.steel",
			"research.techromancy.steel.desc",
			new ResearchRequirement.Scanned("techromancy:steel_ingot")));

	// Research-gated: the Advanced Steam Engine needs steel researched first.
	public static final ResearchEntry ADVANCED_STEAM_ENGINE = ResearchRegistry.register(new ResearchEntry(
			"advanced_steam_engine",
			"research.techromancy.advanced_steam_engine",
			"research.techromancy.advanced_steam_engine.desc",
			new ResearchRequirement.Researched("steel")));

	// Scan-unlocked by discovering mana: opens the Mystic (magic) branch.
	public static final ResearchEntry MYSTIC_STEAM_ENGINE = ResearchRegistry.register(new ResearchEntry(
			"mystic_steam_engine",
			"research.techromancy.mystic_steam_engine",
			"research.techromancy.mystic_steam_engine.desc",
			new ResearchRequirement.Scanned("techromancy:solidified_mana")));

	private ModResearch() {
	}

	/** Forces this class to load so its entries register. Call once during mod setup. */
	public static void init() {
	}
}
