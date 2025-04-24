package com.elementalitems;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public record FireConverterRecipe(Ingredient inputItem, ItemStack output) implements Recipe<PedestalRecipeInput> {

    @Override
    public boolean matches(PedestalRecipeInput input, World world) {
        if(world.isClient()) {
            return false;
        }

        return inputItem.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(PedestalRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return output;
    }

    @Override
    public RecipeSerializer<? extends Recipe<PedestalRecipeInput>> getSerializer() {
        return ModRecipes.FIRE_CONVERTER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<PedestalRecipeInput>> getType() {
        return ModRecipes.FIRE_CONVERTER_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(inputItem);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

       public static class Serializer implements RecipeSerializer<FireConverterRecipe>{

        public static final MapCodec<FireConverterRecipe> CODEC = RecordCodecBuilder.mapCodec(
                        inst -> inst.group(
                                Ingredient.CODEC.fieldOf("ingredient").forGetter(FireConverterRecipe::inputItem),
                                ItemStack.CODEC.fieldOf("result").forGetter(FireConverterRecipe::output)
                                )
                                .apply(inst, FireConverterRecipe::new)
                );

        public static final PacketCodec<RegistryByteBuf, FireConverterRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, FireConverterRecipe::inputItem,
                        ItemStack.PACKET_CODEC, FireConverterRecipe::output,
                        FireConverterRecipe::new
                );

        @Override
        public MapCodec<FireConverterRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FireConverterRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }

}
