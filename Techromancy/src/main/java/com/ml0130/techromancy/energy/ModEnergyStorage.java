package com.ml0130.techromancy.energy;

import net.minecraftforge.energy.EnergyStorage;

/**
 * A Forge Energy buffer for machines. Extends the reference {@link EnergyStorage} with:
 * <ul>
 *   <li>{@link #generate(int)} - add internally produced energy, ignoring the receive limit
 *       (a generator fills its own buffer even though outside blocks cannot push into it);</li>
 *   <li>{@link #setEnergy(int)} - restore a saved amount when the block is loaded;</li>
 *   <li>an {@code onChanged} callback so the owning block entity can mark itself dirty.</li>
 * </ul>
 */
public class ModEnergyStorage extends EnergyStorage {

	private final Runnable onChanged;

	public ModEnergyStorage(int capacity, int maxReceive, int maxExtract, Runnable onChanged) {
		super(capacity, maxReceive, maxExtract);
		this.onChanged = onChanged;
	}

	/** Adds internally generated energy up to the capacity, bypassing the receive limit. */
	public void generate(int amount) {
		if (amount <= 0) {
			return;
		}
		int space = capacity - energy;
		if (space <= 0) {
			return;
		}
		energy += Math.min(space, amount);
		onChanged.run();
	}

	/** Directly sets the stored amount (used when loading from disk). */
	public void setEnergy(int value) {
		energy = Math.max(0, Math.min(capacity, value));
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int received = super.receiveEnergy(maxReceive, simulate);
		if (!simulate && received > 0) {
			onChanged.run();
		}
		return received;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int extracted = super.extractEnergy(maxExtract, simulate);
		if (!simulate && extracted > 0) {
			onChanged.run();
		}
		return extracted;
	}
}
