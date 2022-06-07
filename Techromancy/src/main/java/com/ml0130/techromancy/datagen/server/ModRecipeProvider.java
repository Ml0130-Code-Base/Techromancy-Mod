package com.ml0130.techromancy.datagen.server;

import java.util.function.Consumer;

import com.ml0130.techromancy.init.ItemInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

public class ModRecipeProvider extends RecipeProvider{

	public ModRecipeProvider(DataGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(ItemInit.Solidified_Mana.get())
			.define(null, null);
	}
}
