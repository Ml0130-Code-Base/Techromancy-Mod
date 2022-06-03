package com.ml0130.techromancy.datagen;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.datagen.client.ModBlockStateProvider;
import com.ml0130.techromancy.datagen.client.ModItemModelProvider;
import com.ml0130.techromancy.datagen.client.lang.ModEnUsProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Techromancy.MOD_ID, bus = Bus.MOD)
public class TechromancyDataGeneration {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper(); 
		
			if (event.includeClient()) {
				generator.addProvider(new ModBlockStateProvider(generator, helper));
				generator.addProvider(new ModItemModelProvider(generator, helper));
				generator.addProvider(new ModEnUsProvider(generator));
				
			}
			if (event.includeServer()) {
				
			}
	}

}
