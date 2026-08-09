package com.ml0130.techromancy;

import com.ml0130.techromancy.init.BlockEntityInit;
import com.ml0130.techromancy.init.BlockInit;
import com.ml0130.techromancy.init.ItemInit;
import com.ml0130.techromancy.init.TabInit;
import com.ml0130.techromancy.research.ModResearch;
import com.ml0130.techromancy.research.ResearchCommands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("techromancy")
public class Techromancy {

	public static final String MOD_ID = "techromancy";

	public Techromancy(FMLJavaModLoadingContext context) {
		var modBusGroup = context.getModBusGroup();

		ItemInit.ITEMS.register(modBusGroup);
		BlockInit.BLOCKS.register(modBusGroup);
		BlockEntityInit.BLOCK_ENTITIES.register(modBusGroup);
		TabInit.TABS.register(modBusGroup);

		// Load the selection of research entries (§ research-and-essence-design.md).
		ModResearch.init();

		// Research-sharing commands (/techromancy research invite|accept|decline|leave|status).
		RegisterCommandsEvent.BUS.addListener(ResearchCommands::onRegisterCommands);
	}
}
