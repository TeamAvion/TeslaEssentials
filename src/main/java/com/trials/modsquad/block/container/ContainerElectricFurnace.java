package com.trials.modsquad.block.container;

import com.trials.modsquad.block.tile.TileElectricFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class ContainerElectricFurnace extends Container {

    private TileElectricFurnace furnace;

    public ContainerElectricFurnace(InventoryPlayer inv, TileElectricFurnace furnace){
        this.furnace = furnace;
        addSlotToContainer(new SlotItemHandler(furnace, 0, 56, 35));
        addSlotToContainer(new SlotItemHandler(furnace, 1, 116, 35));

        for(int i = 0; i<3; ++i) for(int j = 0; j<9; ++j) addSlotToContainer(new Slot(inv, j+i*9+9, 8+j*18, 84+i*18));
        for(int i = 0; i<9; ++i) addSlotToContainer(new Slot(inv, i, 8+i*18, 142));
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack i = ItemStack.EMPTY;
        Slot s = inventorySlots.get(index);
        if (s != null && s.getHasStack()) {
            ItemStack is = s.getStack();
            assert ((is != null) || !is.isEmpty());
            i = is.copy();
            if (index < furnace.getSlots()) {
                if (!mergeItemStack(is, furnace.getSlots(), 36 + furnace.getSlots(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!mergeItemStack(is, 0, furnace.getSlots(), false)) {
                return ItemStack.EMPTY;
            }
            if (is.getCount() == 0) {
                s.putStack(ItemStack.EMPTY);
            }
            else {
                s.onSlotChanged();
            }
            if (is.getCount() == i.getCount()) {
                return ItemStack.EMPTY;
            }
            s.onTake(playerIn, is);
        }
        return i;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return furnace.getDistanceSq(playerIn.posX+.5, playerIn.posY+.5, playerIn.posZ+.5)<64;
    }
}
