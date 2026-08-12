package com.ml0130.techromancy.client.screen;

import java.util.List;

import com.ml0130.techromancy.menu.SteamEngineMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Code-drawn screen for the steam engines: a vertical energy bar on the left, a fuel slot with a burn
 * flame in the middle, and the player inventory below. No background texture - everything is drawn with
 * primitives so it themes cleanly and needs no binary asset.
 */
public class SteamEngineScreen extends AbstractContainerScreen<SteamEngineMenu> {

	// Panel colours (ARGB), approximating the vanilla container look.
	private static final int PANEL = 0xFFC6C6C6;
	private static final int PANEL_LIGHT = 0xFFFFFFFF;
	private static final int PANEL_DARK = 0xFF555555;
	private static final int SLOT_BORDER = 0xFF373737;
	private static final int SLOT_FILL = 0xFF8B8B8B;
	private static final int BAR_EMPTY = 0xFF2B2B2B;
	private static final int BAR_FULL = 0xFF43B043;
	private static final int FLAME_LOW = 0xFF7A3B00;
	private static final int FLAME_HIGH = 0xFFF0A020;

	// Local (relative to leftPos/topPos) layout of the custom widgets.
	private static final int ENERGY_X = 10;
	private static final int ENERGY_Y = 16;
	private static final int ENERGY_W = 16;
	private static final int ENERGY_H = 54;
	private static final int FUEL_X = 80;
	private static final int FUEL_Y = 53;
	private static final int FLAME_X = 81;
	private static final int FLAME_Y = 37;
	private static final int FLAME_SIZE = 14;

	public SteamEngineScreen(SteamEngineMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(graphics, mouseX, mouseY, partialTick);
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
		renderEnergyTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		int x = this.leftPos;
		int y = this.topPos;

		// Main panel with a bevelled edge.
		graphics.fill(x, y, x + imageWidth, y + imageHeight, PANEL);
		graphics.fill(x, y, x + imageWidth, y + 1, PANEL_LIGHT);
		graphics.fill(x, y, x + 1, y + imageHeight, PANEL_LIGHT);
		graphics.fill(x, y + imageHeight - 1, x + imageWidth, y + imageHeight, PANEL_DARK);
		graphics.fill(x + imageWidth - 1, y, x + imageWidth, y + imageHeight, PANEL_DARK);

		// Fuel slot + player inventory slot cells.
		drawSlot(graphics, x + FUEL_X, y + FUEL_Y);
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				drawSlot(graphics, x + 8 + col * 18, y + 84 + row * 18);
			}
		}
		for (int col = 0; col < 9; col++) {
			drawSlot(graphics, x + 8 + col * 18, y + 142);
		}

		drawEnergyBar(graphics, x, y);
		drawFlame(graphics, x, y);
	}

	private void drawSlot(GuiGraphics graphics, int x, int y) {
		graphics.fill(x - 1, y - 1, x + 17, y + 17, SLOT_BORDER);
		graphics.fill(x, y, x + 16, y + 16, SLOT_FILL);
	}

	private void drawEnergyBar(GuiGraphics graphics, int x, int y) {
		int bx = x + ENERGY_X;
		int by = y + ENERGY_Y;
		graphics.fill(bx - 1, by - 1, bx + ENERGY_W + 1, by + ENERGY_H + 1, SLOT_BORDER);
		graphics.fill(bx, by, bx + ENERGY_W, by + ENERGY_H, BAR_EMPTY);

		int max = Math.max(1, menu.getMaxEnergyStored());
		int stored = Math.max(0, Math.min(menu.getEnergyStored(), max));
		int filled = (int) ((long) stored * ENERGY_H / max);
		if (filled > 0) {
			graphics.fill(bx, by + ENERGY_H - filled, bx + ENERGY_W, by + ENERGY_H, BAR_FULL);
		}
	}

	private void drawFlame(GuiGraphics graphics, int x, int y) {
		int lit = menu.getLitTime();
		int dur = menu.getLitDuration();
		if (lit <= 0 || dur <= 0) {
			return;
		}
		int fx = x + FLAME_X;
		int fy = y + FLAME_Y;
		int height = Math.max(1, (int) ((long) lit * FLAME_SIZE / dur));
		graphics.fill(fx, fy + (FLAME_SIZE - height), fx + FLAME_SIZE, fy + FLAME_SIZE, FLAME_LOW);
		int inner = Math.max(1, height - 3);
		graphics.fill(fx + 2, fy + (FLAME_SIZE - inner), fx + FLAME_SIZE - 2, fy + FLAME_SIZE, FLAME_HIGH);
	}

	private void renderEnergyTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
		int bx = this.leftPos + ENERGY_X;
		int by = this.topPos + ENERGY_Y;
		if (mouseX >= bx && mouseX < bx + ENERGY_W && mouseY >= by && mouseY < by + ENERGY_H) {
			Component text = Component.literal(menu.getEnergyStored() + " / " + menu.getMaxEnergyStored() + " FE");
			graphics.renderTooltip(this.font, List.of(ClientTooltipComponent.create(text.getVisualOrderText())),
					mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, null);
		}
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
		graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040,
				false);
	}
}
