package com.ml0130.techromancy.research;

/**
 * How a {@link ResearchEntry} becomes unlocked for a player. A small sealed hierarchy so more
 * requirement kinds (AND/OR, "has essence X", "researched at the Discovery Table", ...) can be added
 * later without changing the call sites that evaluate them.
 */
public sealed interface ResearchRequirement {

	/** Always available - e.g. a scan-unlocked topic that needs nothing, or a free/wooden-tier entry. */
	record None() implements ResearchRequirement {
	}

	/**
	 * Unlocked once the player has scanned/identified the thing with this registry id
	 * (e.g. {@code "techromancy:steel_ingot"} or {@code "minecraft:iron_ingot"}).
	 */
	record Scanned(String targetId) implements ResearchRequirement {
	}

	/** Unlocked once the player has completed another research entry, referenced by its id. */
	record Researched(String researchId) implements ResearchRequirement {
	}
}
