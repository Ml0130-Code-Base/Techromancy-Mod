package com.ml0130.techromancy.init;

import com.ml0130.techromancy.Techromancy;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
			Techromancy.MOD_ID);

	public static final RegistryObject<CreativeModeTab> Techromancy_Tab = TABS.register("techromancy",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.techromancy"))
					.icon(() -> ItemInit.Solidified_Mana.get().getDefaultInstance())
					.displayItems((parameters, output) -> {
						// Magic items
						output.accept(ItemInit.Solidified_Mana.get());
						output.accept(ItemInit.Imbued_Gear.get());
						output.accept(ItemInit.Imbued_Glass_Leans.get());
						output.accept(ItemInit.Glass_Leans.get());

						// Steam items
						output.accept(ItemInit.Wooden_Gear.get());
						output.accept(ItemInit.Steel_Gear.get());
						output.accept(ItemInit.Glass_Pipe.get());

						// Ores and ingots
						output.accept(ItemInit.Steel_Ingot.get());
						output.accept(ItemInit.Steel_Nugget.get());

						// Tools
						output.accept(ItemInit.Solidified_Mana_Pickaxe.get());
						output.accept(ItemInit.Solidified_Mana_Axe.get());
						output.accept(ItemInit.Solidified_Mana_Sword.get());
						output.accept(ItemInit.Solidified_Mana_Hoe.get());
						output.accept(ItemInit.Solidified_Mana_Shovel.get());
						output.accept(ItemInit.Steel_Pickaxe.get());
						output.accept(ItemInit.Steel_Axe.get());
						output.accept(ItemInit.Steel_Sword.get());
						output.accept(ItemInit.Steel_Hoe.get());
						output.accept(ItemInit.Steel_Shovel.get());

						// Blocks (added via their block-item form)
						output.accept(BlockInit.Hex_Gate.get());
						output.accept(BlockInit.Mystic_Steam_Engine.get());
						output.accept(BlockInit.Wooden_Steam_Engine.get());
						output.accept(BlockInit.Advanced_Steam_Engine.get());
						output.accept(BlockInit.Compressor.get());
						output.accept(BlockInit.Pressurized_Glass_Pipe.get());
						output.accept(BlockInit.Steel_Block.get());
						output.accept(BlockInit.Discovery_Table.get());
						output.accept(BlockInit.Essence_Striper.get());
					})
					.build());
}
