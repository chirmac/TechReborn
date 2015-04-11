package techreborn.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import techreborn.util.FluidUtils;
import techreborn.util.Inventory;


public class TileQuantumChest extends TileEntity implements IInventory {

    //Slot 0 = Input
    //Slot 1 = Output
    //Slot 2 = Fake Item
    public Inventory inventory = new Inventory(3, "TileQuantumChest", Integer.MAX_VALUE);

    public ItemStack storedItem;


    @Override
    public void updateEntity() {
        if(storedItem != null){
            setInventorySlotContents(2, new ItemStack(storedItem.getItem()));
        } else {
            setInventorySlotContents(2, null);
        }

        if(getStackInSlot(0) != null){
            if(storedItem == null){
                storedItem = getStackInSlot(0);
                setInventorySlotContents(0, null);
            } else if (FluidUtils.isItemEqual(storedItem, getStackInSlot(0), true, true)){
                if(storedItem.stackSize <=Integer.MAX_VALUE - getStackInSlot(0).stackSize){
                    storedItem.stackSize += getStackInSlot(0).stackSize;
                    decrStackSize(0, getStackInSlot(0).stackSize);
                }
            }
        }

        if(storedItem != null && getStackInSlot(1) == null){
            ItemStack itemStack = storedItem.copy();
            itemStack.stackSize = 64;
            setInventorySlotContents(1, itemStack);
            storedItem.stackSize -= 64;
        } else if(FluidUtils.isItemEqual(getStackInSlot(1), storedItem, true, true)){
            int wanted = 64 - getStackInSlot(1).stackSize;
            if(storedItem.stackSize >= wanted){
                decrStackSize(1, -wanted);
                storedItem.stackSize -= wanted;
            } else {
                decrStackSize(1, -storedItem.stackSize);
                storedItem = null;
            }
        }
    }


    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
        readFromNBT(packet.func_148857_g());
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slotId, int count) {
        return inventory.decrStackSize(slotId, count);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public void openInventory() {
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return inventory.isItemValidForSlot(slot, stack);
    }
}
