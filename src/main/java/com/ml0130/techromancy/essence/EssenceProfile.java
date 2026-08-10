package com.ml0130.techromancy.essence;

import java.util.ArrayList;
import java.util.List;

/**
 * An item/block's essence make-up, split by what a scan reveals ("surface") vs what only stripping
 * reveals ("deep"). Some items therefore need <em>both</em> a scan and a strip for the full picture.
 */
public record EssenceProfile(List<EssenceEntry> entries) {

	public static final EssenceProfile EMPTY = new EssenceProfile(List.of());

	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/** Essences revealed just by scanning (the surface ones). */
	public List<EssenceEntry> surface() {
		List<EssenceEntry> out = new ArrayList<>();
		for (EssenceEntry e : entries) {
			if (!e.requiresStrip()) {
				out.add(e);
			}
		}
		return out;
	}

	/** The complete breakdown (what stripping reveals). */
	public List<EssenceEntry> full() {
		return entries;
	}

	/** True if any essence is only revealed by stripping - i.e. scanning alone is not the full story. */
	public boolean needsStripForFull() {
		for (EssenceEntry e : entries) {
			if (e.requiresStrip()) {
				return true;
			}
		}
		return false;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private final List<EssenceEntry> entries = new ArrayList<>();

		/** Adds a surface essence, revealed by scanning. */
		public Builder scan(Essence essence, int amount) {
			entries.add(new EssenceEntry(essence, amount, false));
			return this;
		}

		/** Adds a deep essence, only revealed by stripping. */
		public Builder strip(Essence essence, int amount) {
			entries.add(new EssenceEntry(essence, amount, true));
			return this;
		}

		public EssenceProfile build() {
			return new EssenceProfile(List.copyOf(entries));
		}
	}
}
