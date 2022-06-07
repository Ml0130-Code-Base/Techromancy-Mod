package com.ml0130.techromancy.datagen.client;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.init.BlockInit;
import com.ml0130.techromancy.init.ItemInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider{

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, Techromancy.MOD_ID, helper);
		// TODO Auto-generated constructor stub
	}
	protected void simpleBlockItem(Item item) {
		getBuilder(item.getRegistryName().toString())
		.parent(getExistingFile(modLoc("block/" + item.getRegistryName().getPath())));
	}
	private ItemModelBuilder simpleItem(Item item)
    {
        return withExistingParent(item.getRegistryName().getPath(), 
                new ResourceLocation("item/generated")).texture("layer0", 
                        new ResourceLocation(Techromancy.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item)
    {
        return withExistingParent(item.getRegistryName().getPath(), 
                new ResourceLocation("item/handheld")).texture("layer0", 
                        new ResourceLocation(Techromancy.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }
	@Override
	protected void registerModels() {
		//Blocks
		simpleBlockItem(BlockInit.Hex_Gate.get().asItem());
		simpleBlockItem(BlockInit.Wooden_Steam_Engine.get().asItem());
		simpleBlockItem(BlockInit.Advanced_Steam_Engine.get().asItem());
		simpleBlockItem(BlockInit.Mystic_Steam_Engine.get().asItem());
		simpleBlockItem(BlockInit.Compressor.get().asItem());
		simpleBlockItem(BlockInit.Pressurized_Glass_Pipe.get().asItem()	);
		simpleBlockItem(BlockInit.Steel_Block.get().asItem());
		
		//Items
		simpleItem(ItemInit.Solidified_Mana.get());
		simpleItem(ItemInit.Wooden_Gear.get());
		simpleItem(ItemInit.Steel_Gear.get());
		simpleItem(ItemInit.Imbued_Gear.get());
		simpleItem(ItemInit.Solidified_Mana_Pickaxe.get());
		simpleItem(ItemInit.Solidified_Mana_Axe.get());
		simpleItem(ItemInit.Solidified_Mana_Hoe.get());
		simpleItem(ItemInit.Solidified_Mana_Sword.get());
		simpleItem(ItemInit.Solidified_Mana_Shovel.get());
		simpleItem(ItemInit.Glass_Pipe.get());
		simpleItem(ItemInit.Steel_Ingot.get());
		simpleItem(ItemInit.Steel_Pickaxe.get());
		simpleItem(ItemInit.Steel_Axe.get());
		simpleItem(ItemInit.Steel_Hoe.get());
		simpleItem(ItemInit.Steel_Sword.get());
		
	}

}
