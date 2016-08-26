package com.trials.modsquad.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TeslaCraftingHandler {
    private ArrayList<GrinderRecipe> grinderRecipeList = new ArrayList<>();
    private List<ItemStack> oreDictIn = null, oreDictOut;
    private ItemStack output = null;

    TeslaCraftingHandler(){
        grinderRecipeList.trimToSize();
    }

    public void registerGrinderRecipe(ItemStack in, ItemStack out, int amount)
    {
        this.grinderRecipeList.add(new GrinderRecipe(in, out, amount));
    }

    public void registerGrinderRecipe(String oreDictIn, String oreDictOut, int amount)
    {
        this.oreDictIn = OreDictionary.getOres(oreDictIn, false);
        if(this.oreDictIn != null) {
            output = OreDictionary.getOres(oreDictOut, false).get(0);
            if(output != null){
                for(ItemStack in: this.oreDictIn)
                    this.grinderRecipeList.add(new GrinderRecipe(in, output, amount));
        }}
        this.oreDictIn = null;
        output = null;
    }

    public void registerGrinderRecipe(String oreDictIn, ItemStack out, int amount)
    {
        this.oreDictIn = OreDictionary.getOres(oreDictIn, false);
        if(this.oreDictIn != null) {
            for(ItemStack in: this.oreDictIn)
                this.grinderRecipeList.add(new GrinderRecipe(in, out, amount));
            }
        this.oreDictIn = null;
    }

    public void registerGrinderRecipe(ItemStack in, String oreDictOut, int amount)
    {
        output = OreDictionary.getOres(oreDictOut, false).get(0);
        if(output != null)
            this.grinderRecipeList.add(new GrinderRecipe(in, output, amount));
        output = null;
    }

    private ItemStack getGrinderResult(int index)
    {
        return grinderRecipeList.get(index).getOutput();
    }

    private ItemStack getGrinderInput(int index)
    {
        return grinderRecipeList.get(index).getInput();
    }

    public int getGrinderRecipeIndex(ItemStack in)
    {
        for(int i = 0; i< grinderRecipeList.size(); i++)
        {
            if(in.isItemEqual(getGrinderInput(i)))
            {
                return i;
            }
        }
        return -1;
    }

    public ItemStack getGrinderOutFromIn(ItemStack in)
    {
        int i = getGrinderRecipeIndex(in);
        if(i > -1)
            return getGrinderResult(i);
        return null;
    }

    public GrinderRecipe getGrinderRecipeFromIn(ItemStack in)
    {
        int i = getGrinderRecipeIndex(in);
        if(i > -1)
            return grinderRecipeList.get(i);
        return null;
    }

    public boolean hasRecipe(ItemStack input){ return input!=null && getGrinderOutFromIn(input)!=null; }
}
