package com.ml0130.techromancy;

import com.ml0130.techromancy.init.BlockInit;
import com.ml0130.techromancy.init.ItemInit;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("techromancy")
public class Techromancy {
	
	public static final String MOD_ID = "techromancy";
	
	public static final CreativeModeTab Techromancy_Tab = new CreativeModeTab(MOD_ID) {
		
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			//return new ItemStack(ItemInit.Hex_Gate.get());
			return new ItemStack(ItemInit.Solidified_Mana.get());
		}
	};

	public Techromancy() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
			ItemInit.ITEMS.register(bus);
			BlockInit.BLOCKS.register(bus);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
}
