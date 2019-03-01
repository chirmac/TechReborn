/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.init;

import java.util.function.Supplier;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;


public enum TRArmorMaterial implements IArmorMaterial {

	BRONZE(17, new int[] { 3, 6, 5,	2 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> {
		return Ingredient.fromItems(TRContent.Ingots.BRONZE.asItem());
	}), 
	RUBY(16, new int[] { 2, 7, 5, 2 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F, () -> {
		return Ingredient.fromItems(TRContent.Gems.RUBY.asItem());
	}), 
	SAPPHIRE(19, new int[] { 4, 4, 4, 4 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F, () -> {
				return Ingredient.fromItems(TRContent.Gems.SAPPHIRE.asItem());
			}), 
	PERIDOT(17, new int[] { 3, 8, 3, 2 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F, () -> {
		return Ingredient.fromItems(TRContent.Gems.PERIDOT.asItem());
	}), 
	CLOAKING(5, new int[] { 1, 2, 3, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F, null);

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyLoadBase<Ingredient> repairMaterial;

	private TRArmorMaterial(int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
			SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterialIn) {
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = new LazyLoadBase<>(repairMaterialIn);
	}

	@Override
	public int getDurability(EntityEquipmentSlot slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactor;
	}

	@Override
	public int getDamageReductionAmount(EntityEquipmentSlot slotIn) {
		return damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return soundEvent;
	}

	@Override
	public Ingredient getRepairMaterial() {
		if (repairMaterial != null) {
			return repairMaterial.getValue();
		}
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public float getToughness() {
		return toughness;
	}
}
