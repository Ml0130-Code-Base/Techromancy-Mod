package com.ml0130.techromancy.essence;

import java.util.HashMap;
import java.util.Map;

/**
 * Resolves ANY item/block registry id to its {@link EssenceProfile}, so essences apply to vanilla, this
 * mod, and other mods alike. Layered (see docs/research-and-essence-design.md &sect;4e):
 * <ol>
 *   <li>explicit assignments (registered below / via datapack later);</li>
 *   <li>tag-based defaults - <em>TODO</em>;</li>
 *   <li>recipe-ingredient derivation - <em>TODO</em>;</li>
 *   <li>fallback: {@link EssenceProfile#EMPTY}.</li>
 * </ol>
 * Only layers 1 and 4 exist for now; the others are meant to drop in without changing callers. Resolution
 * never fails - an unknown id returns {@link EssenceProfile#EMPTY}.
 */
public final class EssenceResolver {

	private static final Map<String, EssenceProfile> EXPLICIT = new HashMap<>();

	private EssenceResolver() {
	}

	/** Assigns an explicit profile to a registry id, e.g. {@code "minecraft:iron_ingot"}. */
	public static void assign(String registryId, EssenceProfile profile) {
		EXPLICIT.put(registryId, profile);
	}

	/** The essence profile for a registry id; never null. */
	public static EssenceProfile resolve(String registryId) {
		EssenceProfile explicit = EXPLICIT.get(registryId);
		if (explicit != null) {
			return explicit;
		}
		// TODO layer 2 (tags) and layer 3 (recipe derivation) before the fallback.
		return EssenceProfile.EMPTY;
	}
}
