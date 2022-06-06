package com.ml0130.techromancy.datagen.client;

import com.ml0130.techromancy.Techromancy;
import com.ml0130.techromancy.init.BlockInit;
import com.ml0130.techromancy.init.ItemInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
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
	protected void oneLayerItem(Item item, ResourceLocation texture) {
		ResourceLocation itemTexture = new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath());
		if (existingFileHelper.exists(itemTexture, PackType.CLIENT_RESOURCES, ".png", "texture")) {
			getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/genertated"))).texture("layer0", itemTexture);
		}else {
		System.out.println("Texture for" + item.getRegistryName().toString() + " not present at" + item.getRegistryName().toString());
	}
	}
	protected void oneLayerItem(Item item) {
		oneLayerItem(item, item.getRegistryName());
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
		
		//Items
		oneLayerItem(ItemInit.Solidified_Mana.get());
		oneLayerItem(ItemInit.Wooden_Gear.get());
		oneLayerItem(ItemInit.Metal_Gear.get());
		oneLayerItem(ItemInit.Imbued_Gear.get());
		oneLayerItem(ItemInit.Solidified_Mana_Pickaxe.get());
		oneLayerItem(ItemInit.Solidified_Mana_Axe.get());
		oneLayerItem(ItemInit.Solidified_Mana_Hoe.get());
		oneLayerItem(ItemInit.Solidified_Mana_Sword.get());
		oneLayerItem(ItemInit.Glass_Pipe.get());
		oneLayerItem(ItemInit.Solidified_Mana_Shovel.get());
		
	}

}
