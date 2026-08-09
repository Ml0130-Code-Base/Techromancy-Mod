package com.ml0130.techromancy.research;

/**
 * One researchable / discoverable topic - the unit of the "selection for research".
 *
 * @param id             unique id within the mod, e.g. {@code "advanced_steam_engine"}
 * @param titleKey       lang key for the display name
 * @param descriptionKey lang key for the flavour / description text
 * @param requirement    what a player must do to unlock this entry
 */
public record ResearchEntry(String id, String titleKey, String descriptionKey, ResearchRequirement requirement) {

	public ResearchEntry {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("Research entry id must not be blank");
		}
		if (requirement == null) {
			throw new IllegalArgumentException("Research entry '" + id + "' must have a requirement");
		}
	}

	/** True if this entry has no prerequisite and is available from the start. */
	public boolean isFree() {
		return requirement instanceof ResearchRequirement.None;
	}
}
