package com.ml0130.techromancy.research;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The in-memory selection of every {@link ResearchEntry} in the mod. Entries are registered from Java
 * for now (a data-driven / datapack version can replace this later without changing readers). Insertion
 * order is preserved so any listing/GUI stays stable.
 */
public final class ResearchRegistry {

	private static final Map<String, ResearchEntry> ENTRIES = new LinkedHashMap<>();

	private ResearchRegistry() {
	}

	/** Registers an entry and returns it (for convenient {@code static final} assignment). Rejects duplicate ids. */
	public static ResearchEntry register(ResearchEntry entry) {
		if (ENTRIES.putIfAbsent(entry.id(), entry) != null) {
			throw new IllegalStateException("Duplicate research entry id: " + entry.id());
		}
		return entry;
	}

	/** The entry with this id, or {@code null} if none is registered. */
	public static ResearchEntry get(String id) {
		return ENTRIES.get(id);
	}

	public static boolean contains(String id) {
		return ENTRIES.containsKey(id);
	}

	/** All registered entries, in registration order (unmodifiable). */
	public static Collection<ResearchEntry> all() {
		return Collections.unmodifiableCollection(ENTRIES.values());
	}

	public static int count() {
		return ENTRIES.size();
	}
}
