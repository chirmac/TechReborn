package techreborn.blocks.generator;

import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import techreborn.client.GuiType;

public class BlockFissionControlComputer extends BlockMachineBase {

	@Override
	public boolean isAdvanced() {
		return true;
	}

	@Override
	public IMachineGuiHandler getGui() {
		return GuiType.FUSION_CONTROLLER;
	}
}
