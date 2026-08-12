package com.ml0130.techromancy.menu;

import com.ml0130.techromancy.block.steam.AbstractSteamEngineBlockEntity;
import com.ml0130.techromancy.init.MenuInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Container menu for every steam engine tier. One fuel slot plus the player inventory; the energy buffer
 * and burn timer are synced through a {@link ContainerData}. The fuel slot's stack is synced by the usual
 * slot mechanism, so the client sees a live view without reading the block entity's energy field.
 */
public class SteamEngineMenu extends AbstractContainerMenu {

	private static final int FUEL_SLOT = 0;
	private static final int SLOT_COUNT = 1; // machine slots before the player inventory

	private final ContainerData data;
	private final ContainerLevelAccess access;
	private final AbstractSteamEngineBlockEntity blockEntity; // may be null defensively; capacity read for the bar

	/** Server-side: real block entity, live data. */
	public SteamEngineMenu(int containerId, Inventory playerInventory, AbstractSteamEngineBlockEntity be) {
		this(containerId, playerInventory, be, be.getDataAccess(),
				ContainerLevelAccess.create(be.getLevel(), be.getBlockPos()));
	}

	/** Client-side: resolve the block entity from the buffer's position; data arrives via sync. */
	public SteamEngineMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
		this(containerId, playerInventory, buf.readBlockPos());
	}

	private SteamEngineMenu(int containerId, Inventory playerInventory, BlockPos clientPos) {
		this(containerId, playerInventory, resolve(playerInventory, clientPos), new SimpleContainerData(4),
				ContainerLevelAccess.NULL);
	}

	private SteamEngineMenu(int containerId, Inventory playerInventory, AbstractSteamEngineBlockEntity be,
			ContainerData data, ContainerLevelAccess access) {
		super(MenuInit.STEAM_ENGINE.get(), containerId);
		this.blockEntity = be;
		this.data = data;
		this.access = access;

		IItemHandler fuelHandler = be != null ? be.getFuelHandler() : new ItemStackHandler(1);
		addSlot(new SlotItemHandler(fuelHandler, FUEL_SLOT, 80, 53));
		addPlayerInventory(playerInventory);
		addDataSlots(data);
	}

	private static AbstractSteamEngineBlockEntity resolve(Inventory inv, BlockPos pos) {
		BlockEntity be = inv.player.level().getBlockEntity(pos);
		return be instanceof AbstractSteamEngineBlockEntity engine ? engine : null;
	}

	private void addPlayerInventory(Inventory inv) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
			}
		}
		for (int col = 0; col < 9; col++) {
			addSlot(new Slot(inv, col, 8 + col * 18, 142));
		}
	}

	// --- synced readouts (used by the screen) --------------------------------

	public int getEnergyStored() {
		return (data.get(0) & 0xFFFF) | (data.get(1) << 16);
	}

	public int getMaxEnergyStored() {
		return blockEntity != null ? blockEntity.getMaxEnergyStored() : 1;
	}

	public int getLitTime() {
		return data.get(2);
	}

	public int getLitDuration() {
		return data.get(3);
	}

	public boolean isLit() {
		return getLitTime() > 0;
	}

	// --- vanilla plumbing ----------------------------------------------------

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack moved = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if (slot == null || !slot.hasItem()) {
			return moved;
		}
		ItemStack stack = slot.getItem();
		moved = stack.copy();

		int invStart = SLOT_COUNT;
		int invEnd = SLOT_COUNT + 36;

		if (index < invStart) {
			// machine -> player inventory
			if (!moveItemStackTo(stack, invStart, invEnd, true)) {
				return ItemStack.EMPTY;
			}
		} else {
			// player inventory -> fuel slot
			if (!moveItemStackTo(stack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
				return ItemStack.EMPTY;
			}
		}

		if (stack.isEmpty()) {
			slot.set(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}
		if (stack.getCount() == moved.getCount()) {
			return ItemStack.EMPTY;
		}
		slot.onTake(player, stack);
		return moved;
	}

	@Override
	public boolean stillValid(Player player) {
		return access.evaluate(
				(level, pos) -> player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0,
				true);
	}
}
