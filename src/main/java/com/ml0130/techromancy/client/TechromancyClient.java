package com.ml0130.techromancy.client;

import com.ml0130.techromancy.client.screen.SteamEngineScreen;
import com.ml0130.techromancy.init.MenuInit;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-only wiring. Registers the screen factories for the mod's menus. Invoked from the main mod
 * constructor, guarded by {@code FMLEnvironment.dist}, so none of these client-only classes load on a
 * dedicated server.
 */
public final class TechromancyClient {

	private TechromancyClient() {
	}

	/** Hook the client-setup listener onto the mod event bus. */
	public static void register(BusGroup modBusGroup) {
		FMLClientSetupEvent.getBus(modBusGroup).addListener(TechromancyClient::onClientSetup);
	}

	private static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> MenuScreens.register(MenuInit.STEAM_ENGINE.get(), SteamEngineScreen::new));
	}
}
