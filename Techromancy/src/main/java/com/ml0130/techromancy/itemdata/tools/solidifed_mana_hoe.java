package com.ml0130.techromancy.itemdata.tools;

import java.util.function.Consumer;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class solidifed_mana_hoe extends HoeItem{

	public solidifed_mana_hoe(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_) {
		super(p_41336_, p_41337_, p_41338_, p_41339_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getAttackDamage() {
		// TODO Auto-generated method stub
		return super.getAttackDamage();
	}
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		// TODO Auto-generated method stub
		return super.damageItem(stack, amount, entity, onBroken);
	}
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
		// TODO Auto-generated method stub
		return super.use(p_41432_, p_41433_, p_41434_);
	}
}