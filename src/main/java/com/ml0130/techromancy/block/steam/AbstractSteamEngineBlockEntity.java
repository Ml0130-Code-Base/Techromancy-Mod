package com.ml0130.techromancy.block.steam;

import com.ml0130.techromancy.energy.ModEnergyStorage;
import com.ml0130.techromancy.menu.SteamEngineMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Shared logic for every steam engine tier: burn furnace fuel to fill a Forge Energy buffer and push that
 * energy to adjacent blocks. Fuel is drawn from a single fuel slot (fed via the GUI, or by pipes/hoppers
 * through the item-handler capability). Concrete tiers only supply their capacity / output / generation.
 */
public abstract class AbstractSteamEngineBlockEntity extends BlockEntity implements MenuProvider {

	private final ModEnergyStorage energy;
	private final LazyOptional<IEnergyStorage> energyCap;
	private final int maxOutput;   // FE/tick pushed to each neighbour
	private final int generation;  // FE/tick produced while burning

	private final ItemStackHandler fuel = new ItemStackHandler(1) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};
	private final LazyOptional<IItemHandler> fuelCap = LazyOptional.of(() -> fuel);

	private int litTime;     // ticks of fuel remaining
	private int litDuration; // ticks the current fuel batch lasts (drives the flame indicator)

	/** Menu-synced view of the dynamic state. Energy is split into two 16-bit halves so it survives the
	 *  short-based data-slot packet (buffers exceed 32767 FE). */
	private final ContainerData data = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> energy.getEnergyStored() & 0xFFFF;
				case 1 -> (energy.getEnergyStored() >> 16) & 0xFFFF;
				case 2 -> Math.min(litTime, 0x7FFF);
				case 3 -> Math.min(litDuration, 0x7FFF);
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			// Server never receives these; the client menu uses its own SimpleContainerData.
		}

		@Override
		public int getCount() {
			return 4;
		}
	};

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

		if (be.litTime <= 0) {
			be.tryConsumeFuel(level);
		}

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

	/** Pulls one fuel item from the fuel slot and lights the engine, if there is room in the buffer. */
	private void tryConsumeFuel(Level level) {
		if (energy.getEnergyStored() >= energy.getMaxEnergyStored()) {
			return; // buffer full - don't waste fuel
		}
		ItemStack f = fuel.getStackInSlot(0);
		if (f.isEmpty()) {
			return;
		}
		int burn = level.fuelValues().burnDuration(f);
		if (burn <= 0) {
			return;
		}
		fuel.extractItem(0, 1, false);
		addFuel(burn);
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

	public IItemHandler getFuelHandler() {
		return fuel;
	}

	public ContainerData getDataAccess() {
		return data;
	}

	// --- MenuProvider --------------------------------------------------------

	@Override
	public Component getDisplayName() {
		return getBlockState().getBlock().getName();
	}

	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new SteamEngineMenu(containerId, playerInventory, this);
	}

	// --- capabilities / persistence -----------------------------------------

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY) {
			return energyCap.cast();
		}
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return fuelCap.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		energyCap.invalidate();
		fuelCap.invalidate();
	}

	@Override
	protected void saveAdditional(ValueOutput out) {
		super.saveAdditional(out);
		out.putInt("Energy", energy.getEnergyStored());
		out.putInt("LitTime", litTime);
		out.putInt("LitDuration", litDuration);
		out.store("Fuel", ItemStack.OPTIONAL_CODEC, fuel.getStackInSlot(0));
	}

	@Override
	protected void loadAdditional(ValueInput in) {
		super.loadAdditional(in);
		energy.setEnergy(in.getIntOr("Energy", 0));
		litTime = in.getIntOr("LitTime", 0);
		litDuration = in.getIntOr("LitDuration", 0);
		in.read("Fuel", ItemStack.OPTIONAL_CODEC).ifPresent(s -> fuel.setStackInSlot(0, s));
	}
}
