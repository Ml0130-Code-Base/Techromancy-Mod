package com.ml0130.techromancy.datagen.client.lang;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.init.BlockInit;
import com.ml0130.techromancy.init.ItemInit;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsProvider extends LanguageProvider{

	public ModEnUsProvider(DataGenerator gen) {
		super(gen, Techromancy.MOD_ID, "en_us");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addTranslations() {
		
		//Creative Tabs
		add("itemGroup.techromancy", "Techromancy");
		
		//Items
		add(ItemInit.Solidified_Mana.get(), "Solidified Mana");
		add(ItemInit.Wooden_Gear.get(), "Wooden Gear");
		add(ItemInit.Steel_Gear.get(), "Steel Gear");
		add(ItemInit.Imbued_Gear.get(), "Imbued Gear");
		add(ItemInit.Glass_Pipe.get(), "Glass Pipe");
		add(ItemInit.Solidified_Mana_Axe.get(), "Solidified Mana Axe");
		add(ItemInit.Solidified_Mana_Hoe.get(), "Solidified Mana Hoe");
		add(ItemInit.Solidified_Mana_Pickaxe.get(), "Solidified Mana Pickaxe");
		add(ItemInit.Solidified_Mana_Sword.get(), "Solidified Mana Sword");
		add(ItemInit.Solidified_Mana_Shovel.get(), "Solidified Mana Shovel");
		add(ItemInit.Steel_Axe.get(), "Steel Axe");
		add(ItemInit.Steel_Hoe.get(), "Steel Hoe");
		add(ItemInit.Steel_Pickaxe.get(), "Steel Pickaxe");
		add(ItemInit.Steel_Sword.get(), "Steel Sword");
		add(ItemInit.Steel_Shovel.get(), "Steel Shovel");
		
		//Ores and Ingots
		add(ItemInit.Steel_Ingot.get(), "Steel Ingot");
		

		//Blocks
		add(BlockInit.Hex_Gate.get(), "Hex Gate");
		add(BlockInit.Wooden_Steam_Engine.get(), "Wooden Steam Engine");
		add(BlockInit.Advanced_Steam_Engine.get(), "Advanced Steam Engine");
		add(BlockInit.Mystic_Steam_Engine.get(), "Mystic Steam Engine");
		add(BlockInit.Compressor.get(), "Compressor");
		add(BlockInit.Pressurized_Glass_Pipe.get(), "Pressurized Glass Pipe");
		add(BlockInit.Steel_Block.get(), "Steel Block");
		add(BlockInit.Discovery_Table.get(), "Discovery Table");
}
}