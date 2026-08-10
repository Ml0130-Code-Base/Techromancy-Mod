package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.energy.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Shared logic for every steam engine tier: burn furnace fuel to fill a Forge Energy buffer and push
 * that energy to adjacent blocks. Concrete tiers only supply their capacity / output / generation rate.
 */
public abstract class AbstractSteamEngineBlockEntity extends BlockEntity {

	private final ModEnergyStorage energy;
	private final LazyOptional<IEnergyStorage> energyCap;
	private final int maxOutput;   // FE/tick pushed to each neighbour
	private final int generation;  // FE/tick produced while burning

	private int litTime;     // ticks of fuel remaining
	private int litDuration; // ticks the current fuel batch lasts (kept for a future progress bar)

	protected AbstractSteamEngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int capacity,
			int maxOutput, int generation) {
		super(type, pos, state);
		this.maxOutput = maxOutput;
		this.generation = generation;
		this.energy = new ModEnergyStorage(capacity, 0, maxOutput, this::setChanged);
		this.energyCap = LazyOptional.of(() -> energy);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractSteamEngineBlockEntity be) {
		boolean wasLit = be.litTime > 0;

		if (be.litTime > 0) {
			be.litTime--;
			be.energy.generate(be.generation);
		}

		be.pushEnergy(level, pos);

		boolean lit = be.litTime > 0;
		if (wasLit != lit) {
			level.setBlock(pos, state.setValue(BlockStateProperties.LIT, lit), 3);
			be.setChanged();
		}
	}

	private void pushEnergy(Level level, BlockPos pos) {
		if (energy.getEnergyStored() <= 0) {
			return;
		}
		for (Direction dir : Direction.values()) {
			BlockEntity neighbor = level.getBlockEntity(pos.relative(dir));
			if (neighbor == null) {
				continue;
			}
			neighbor.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite()).ifPresent(dest -> {
				if (dest.canReceive()) {
					int offered = energy.extractEnergy(maxOutput, true);
					int accepted = dest.receiveEnergy(offered, false);
					if (accepted > 0) {
						energy.extractEnergy(accepted, false);
					}
				}
			});
			if (energy.getEnergyStored() <= 0) {
				break;
			}
		}
	}

	/** Adds burn time from a consumed fuel item and lights the engine. */
	public void addFuel(int burnDuration) {
		this.litTime += burnDuration;
		this.litDuration = burnDuration;
		setChanged();
	}

	public boolean isLit() {
		return litTime > 0;
	}

	public int getEnergyStored() {
		return energy.getEnergyStored();
	}

	public int getMaxEnergyStored() {
		return energy.getMaxEnergyStored();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY) {
			return energyCap.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		energyCap.invalidate();
	}

	@Override
	protected void saveAdditional(ValueOutput out) {
		super.saveAdditional(out);
		out.putInt("Energy", energy.getEnergyStored());
		out.putInt("LitTime", litTime);
		out.putInt("LitDuration", litDuration);
	}

	@Override
	protected void loadAdditional(ValueInput in) {
		super.loadAdditional(in);
		energy.setEnergy(in.getIntOr("Energy", 0));
		litTime = in.getIntOr("LitTime", 0);
		litDuration = in.getIntOr("LitDuration", 0);
	}
}
