package com.ml0130.techromancy.essence;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** The set of every {@link Essence} in the mod. Registered from Java (see {@link ModEssences}). */
public final class EssenceRegistry {

	private static final Map<String, Essence> ESSENCES = new LinkedHashMap<>();

	private EssenceRegistry() {
	}

	public static Essence register(Essence essence) {
		if (ESSENCES.putIfAbsent(essence.id(), essence) != null) {
			throw new IllegalStateException("Duplicate essence id: " + essence.id());
		}
		return essence;
	}

	public static Essence get(String id) {
		return ESSENCES.get(id);
	}

	public static Collection<Essence> all() {
		return Collections.unmodifiableCollection(ESSENCES.values());
	}
}
