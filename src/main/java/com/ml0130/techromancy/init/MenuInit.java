package com.ml0130.techromancy.init;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.menu.SteamEngineMenu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Container menu types for the mod's machine GUIs. Each menu is created with {@link IForgeMenuType#create}
 * so the client factory receives the extra data buffer (the machine's block position) written when the
 * server opens the screen.
 */
public class MenuInit {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,
			Techromancy.MOD_ID);

	public static final RegistryObject<MenuType<SteamEngineMenu>> STEAM_ENGINE = MENUS.register("steam_engine",
			() -> IForgeMenuType.create(SteamEngineMenu::new));

	private MenuInit() {
	}
}
