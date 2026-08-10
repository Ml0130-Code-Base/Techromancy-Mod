package com.ml0130.techromancy.essence;

/**
 * A dual-reading essence: one concept with a mechanical face and a mystical face. Identified by a short
 * {@link #id()}. Lang keys are derived from the id; {@link #color()} is for future UI tint.
 */
public record Essence(String id, int color) {

	public String translationKey() {
		return "essence.techromancy." + id;
	}

	public String techFaceKey() {
		return translationKey() + ".tech";
	}

	public String magicFaceKey() {
		return translationKey() + ".magic";
	}

	/** Fallback display name (capitalised id) for chat readouts until lang entries exist. */
	public String display() {
		return id.isEmpty() ? id : Character.toUpperCase(id.charAt(0)) + id.substring(1);
	}
}
