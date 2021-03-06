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

package techreborn.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.powerSystem.PowerSystem;
import techreborn.blockentity.storage.energy.MediumVoltageSUBlockEntity;

public class GuiMFE extends GuiBase<BuiltScreenHandler> {

	MediumVoltageSUBlockEntity mfe;

	public GuiMFE(int syncID, final PlayerEntity player, final MediumVoltageSUBlockEntity mfe) {
		super(player, mfe, mfe.createScreenHandler(syncID, player));
		this.mfe = mfe;
	}

	@Override
	protected void drawBackground(MatrixStack matrixStack, final float f, final int mouseX, final int mouseY) {
		super.drawBackground(matrixStack, f, mouseX, mouseY);
		final Layer layer = Layer.BACKGROUND;

		drawSlot(matrixStack, 62, 45, layer);
		drawSlot(matrixStack, 98, 45, layer);
		drawArmourSlots(matrixStack, 8, 18, layer);
	}

	@Override
	protected void drawForeground(MatrixStack matrixStack, final int mouseX, final int mouseY) {
		super.drawForeground(matrixStack, mouseX, mouseY);
		final Layer layer = Layer.FOREGROUND;

		if (!hideGuiElements()) {
			RenderSystem.pushMatrix();
			RenderSystem.scaled(0.6, 0.6, 1);

			drawCentredText(matrixStack, new LiteralText(PowerSystem.getLocaliszedPowerFormattedNoSuffix((int) mfe.getEnergy()))
							.append("/")
							.append(PowerSystem.getLocaliszedPowerFormattedNoSuffix((int) mfe.getMaxPower()))
							.append(" ")
							.append(PowerSystem.getDisplayPower().abbreviation)
					, 35, 0, 58, layer);

			RenderSystem.popMatrix();
		}

		builder.drawMultiEnergyBar(matrixStack, this, 81, 28, (int) mfe.getEnergy(), (int) mfe.getMaxPower(), mouseX, mouseY, 0, layer);
	}
}
