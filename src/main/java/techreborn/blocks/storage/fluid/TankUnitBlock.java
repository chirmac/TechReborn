/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2020 TechReborn
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

package techreborn.blocks.storage.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.fluid.container.ItemFluidInfo;
import reborncore.common.util.Tank;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;
import techreborn.client.GuiType;
import techreborn.init.TRContent;

public class TankUnitBlock extends BlockMachineBase {

	public final TRContent.TankUnit unitType;

	public TankUnitBlock(TRContent.TankUnit unitType) {
		super();
		this.unitType = unitType;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView worldIn) {
		return new TankUnitBaseBlockEntity(unitType);
	}

	@Override
	public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockHitResult hitResult) {
		if (unitType == TRContent.TankUnit.CREATIVE || worldIn.isClient) {
			return super.onUse(state, worldIn, pos, playerIn, hand, hitResult);
		}

		final TankUnitBaseBlockEntity tankUnitEntity = (TankUnitBaseBlockEntity) worldIn.getBlockEntity(pos);
		ItemStack stackInHand = playerIn.getStackInHand(Hand.MAIN_HAND);
		Item itemInHand = stackInHand.getItem();

		// Assuming ItemFluidInfo is 1 BUCKET, for now only allow exact amount or less
		if (tankUnitEntity != null && itemInHand instanceof ItemFluidInfo) {
			ItemFluidInfo itemFluid = (ItemFluidInfo) itemInHand;
			Fluid fluid = itemFluid.getFluid(stackInHand);
			int amount = stackInHand.getCount();

			FluidValue fluidValue = FluidValue.BUCKET.multiply(amount);
			Tank tankInstance = tankUnitEntity.getTank();

			if (tankInstance.canFit(fluid, fluidValue)) {
				if (tankInstance.getFluidInstance().isEmptyFluid()) {
					tankInstance.setFluidInstance(new FluidInstance(fluid, fluidValue));
				} else {
					tankInstance.getFluidInstance().addAmount(fluidValue);
				}

				ItemStack returnStack = itemFluid.getEmpty();
				returnStack.setCount(amount);
				playerIn.setStackInHand(Hand.MAIN_HAND, returnStack);
			}

			return ActionResult.SUCCESS;
		}


		return super.onUse(state, worldIn, pos, playerIn, hand, hitResult);
	}

	@Override
	public IMachineGuiHandler getGui() {
		return GuiType.TANK_UNIT;
	}
}
