package com.ml0130.techromancy.block.steam;

import java.util.Optional;

import com.ml0130.techromancy.energy.ModEnergyStorage;
import com.ml0130.techromancy.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * A basic FE-powered smelter: slot 0 = input, slot 1 = output. While it has power and a valid vanilla
 * smelting recipe, it consumes {@link #ENERGY_PER_TICK} FE/tick for {@link #TOTAL_TIME} ticks, then
 * produces the result. Exposes the Forge Energy and Item Handler capabilities, so pipes/hoppers can feed
 * and drain it. (A GUI + upgrades are a later pass.)
 */
public class SmelterBlockEntity extends BlockEntity {

	public static final int CAPACITY = 20_000;
	public static final int MAX_RECEIVE = 400;   // FE/tick it can accept
	public static final int ENERGY_PER_TICK = 20; // FE/tick spent while smelting
	public static final int TOTAL_TIME = 100;     // ticks per smelt

	private final ModEnergyStorage energy = new ModEnergyStorage(CAPACITY, MAX_RECEIVE, 0, this::setChanged);
	private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);
	private final ItemStackHandler items = new ItemStackHandler(2) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};
	private final LazyOptional<IItemHandler> itemCap = LazyOptional.of(() -> items);

	private int progress;

	public SmelterBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.SMELTER.get(), pos, state);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, SmelterBlockEntity be) {
		be.smelt(level);
	}

	private void smelt(Level level) {
		ItemStack input = items.getStackInSlot(0);
		if (input.isEmpty()) {
			progress = 0;
			return;
		}
		SingleRecipeInput recipeInput = new SingleRecipeInput(input);
		Optional<RecipeHolder<SmeltingRecipe>> match = level.getServer().getRecipeManager()
				.getRecipeFor(RecipeType.SMELTING, recipeInput, level);
		if (match.isEmpty()) {
			progress = 0;
			return;
		}
		ItemStack result = match.get().value().assemble(recipeInput, level.registryAccess());
		if (!canOutput(items.getStackInSlot(1), result)) {
			progress = 0;
			return;
		}
		if (!energy.consume(ENERGY_PER_TICK)) {
			return; // no power - stall, keep progress
		}
		progress++;
		if (progress >= TOTAL_TIME) {
			progress = 0;
			items.extractItem(0, 1, false);
			items.insertItem(1, result.copy(), false);
			setChanged();
		}
	}

	private static boolean canOutput(ItemStack output, ItemStack result) {
		if (output.isEmpty()) {
			return true;
		}
		return ItemStack.isSameItemSameComponents(output, result)
				&& output.getCount() + result.getCount() <= output.getMaxStackSize();
	}

	public ItemStackHandler getItems() {
		return items;
	}

	public int getEnergyStored() {
		return energy.getEnergyStored();
	}

	public boolean isWorking() {
		return progress > 0;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY) {
			return energyCap.cast();
		}
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return itemCap.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		energyCap.invalidate();
		itemCap.invalidate();
	}

	@Override
	protected void saveAdditional(ValueOutput out) {
		super.saveAdditional(out);
		out.putInt("Energy", energy.getEnergyStored());
		out.putInt("Progress", progress);
		out.store("Input", ItemStack.OPTIONAL_CODEC, items.getStackInSlot(0));
		out.store("Output", ItemStack.OPTIONAL_CODEC, items.getStackInSlot(1));
	}

	@Override
	protected void loadAdditional(ValueInput in) {
		super.loadAdditional(in);
		energy.setEnergy(in.getIntOr("Energy", 0));
		progress = in.getIntOr("Progress", 0);
		in.read("Input", ItemStack.OPTIONAL_CODEC).ifPresent(s -> items.setStackInSlot(0, s));
		in.read("Output", ItemStack.OPTIONAL_CODEC).ifPresent(s -> items.setStackInSlot(1, s));
	}
}
