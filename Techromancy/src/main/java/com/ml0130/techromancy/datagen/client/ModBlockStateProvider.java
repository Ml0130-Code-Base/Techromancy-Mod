package com.ml0130.techromancy.datagen.client;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.init.BlockInit;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator genertor, ExistingFileHelper helper) {
		super(genertor, Techromancy.MOD_ID, helper);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(BlockInit.Hex_Gate.get());
		simpleBlock(BlockInit.Wooden_Steam_Engine.get());
		simpleBlock(BlockInit.Advanced_Steam_Engine.get());
		simpleBlock(BlockInit.Mystic_Steam_Engine.get());
		simpleBlock(BlockInit.Compressor.get());
		simpleBlock(BlockInit.Pressurized_Glass_Pipe.get());
		simpleBlock(BlockInit.Steel_Block.get());
		
	}

}
