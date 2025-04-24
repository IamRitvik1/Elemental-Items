package com.elementalitems;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record PedestalRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
