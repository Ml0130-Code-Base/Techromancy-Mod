package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.energy.ModEnergyStorage;
import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * The Glass Pipe (a.k.a. Pressurized Glass Pipe) as a Forge Energy conduit: it buffers FE that generators
 * push into it and each tick distributes that FE to adjacent consumers and, "downhill", to neighbouring
 * pipes - so power flows across distance toward whatever is drawing it. (Item transport is a later pass
 * on this same pipe.)
 */
public class PressurizedGlassPipeBlockEntity extends BlockEntity {

	public static final int THROUGHPUT = 512; // FE/tick moved per connection
	public static final int BUFFER = 2048;     // small internal buffer

	private final ModEnergyStorage energy = new ModEnergyStorage(BUFFER, THROUGHPUT, THROUGHPUT, this::setChanged);
	private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);

	public PressurizedGlassPipeBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.PRESSURIZED_GLASS_PIPE.get(), pos, state);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, PressurizedGlassPipeBlockEntity be) {
		be.distribute(level, pos);
	}

	private void distribute(Level level, BlockPos pos) {
		if (energy.getEnergyStored() <= 0) {
			return;
		}
		int mine = energy.getEnergyStored();
		for (Direction dir : Direction.values()) {
			BlockEntity neighbor = level.getBlockEntity(pos.relative(dir));
			if (neighbor == null) {
				continue;
			}
			// Flow only "downhill" into other pipes (prevents two pipes ping-ponging); push freely into consumers.
			if (neighbor instanceof PressurizedGlassPipeBlockEntity pipe && pipe.energy.getEnergyStored() >= mine) {
				continue;
			}
			neighbor.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite()).ifPresent(dest -> {
				if (dest.canReceive()) {
					int offered = energy.extractEnergy(THROUGHPUT, true);
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
	}

	@Override
	protected void loadAdditional(ValueInput in) {
		super.loadAdditional(in);
		energy.setEnergy(in.getIntOr("Energy", 0));
	}
}
