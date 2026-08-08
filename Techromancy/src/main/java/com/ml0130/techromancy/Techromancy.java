package com.ml0130.techromancy;

import com.ml0130.techromancy.init.BlockInit;
import com.ml0130.techromancy.init.ItemInit;
import com.ml0130.techromancy.init.TabInit;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("techromancy")
public class Techromancy {

	public static final String MOD_ID = "techromancy";

	public Techromancy(FMLJavaModLoadingContext context) {
		var modBusGroup = context.getModBusGroup();

		ItemInit.ITEMS.register(modBusGroup);
		BlockInit.BLOCKS.register(modBusGroup);
		TabInit.TABS.register(modBusGroup);
	}
}
