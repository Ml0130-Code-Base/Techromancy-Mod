package com.ml0130.techromancy.essence;

/**
 * The starter set of dual-reading essences, plus some default composition assignments. Names use the
 * "mixed" scheme: grounded/tech essences stay plain, the magical ones get arcane names. Edit freely.
 */
public final class ModEssences {

	public static final Essence HEAT = EssenceRegistry.register(new Essence("heat", 0xE0662C));
	public static final Essence MOTION = EssenceRegistry.register(new Essence("motion", 0x7FB2E0));
	public static final Essence METAL = EssenceRegistry.register(new Essence("metal", 0x9AA0A6));
	public static final Essence VAPOR = EssenceRegistry.register(new Essence("vapor", 0xD8E6EC));
	public static final Essence ARCANUM = EssenceRegistry.register(new Essence("arcanum", 0x8250B8));
	public static final Essence ANIMA = EssenceRegistry.register(new Essence("anima", 0x3FA05A));

	private ModEssences() {
	}

	/** Loads the essences and their default assignments. Call once during mod setup. */
	public static void init() {
		registerDefaultAssignments();
	}

	private static void registerDefaultAssignments() {
		// Examples so the tools show something. Items with a strip(...) essence need BOTH a scan and a
		// strip to see everything (the deep essence is hidden until stripped).
		EssenceResolver.assign("minecraft:coal", EssenceProfile.builder().scan(HEAT, 2).build());
		EssenceResolver.assign("minecraft:charcoal", EssenceProfile.builder().scan(HEAT, 2).build());
		EssenceResolver.assign("minecraft:iron_ingot", EssenceProfile.builder().scan(METAL, 2).build());
		EssenceResolver.assign("minecraft:redstone", EssenceProfile.builder().scan(MOTION, 1).strip(ARCANUM, 1).build());
		EssenceResolver.assign("minecraft:wheat", EssenceProfile.builder().scan(ANIMA, 2).build());
		EssenceResolver.assign("minecraft:water_bucket", EssenceProfile.builder().scan(VAPOR, 2).build());

		EssenceResolver.assign("techromancy:steel_ingot",
				EssenceProfile.builder().scan(METAL, 3).strip(HEAT, 1).build());
		EssenceResolver.assign("techromancy:steel_gear",
				EssenceProfile.builder().scan(METAL, 2).scan(MOTION, 2).build());
		EssenceResolver.assign("techromancy:solidified_mana",
				EssenceProfile.builder().scan(ARCANUM, 3).strip(ANIMA, 1).build());
		EssenceResolver.assign("techromancy:imbued_gear",
				EssenceProfile.builder().scan(METAL, 2).scan(MOTION, 2).strip(ARCANUM, 2).build());
	}
}
