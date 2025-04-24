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

public record WaterConverterRecipe(Ingredient inputItem, ItemStack output) implements Recipe<PedestalRecipeInput> {

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
        return ModRecipes.WATER_CONVERTER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<PedestalRecipeInput>> getType() {
        return ModRecipes.WATER_CONVERTER_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(inputItem);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

       public static class Serializer implements RecipeSerializer<WaterConverterRecipe>{

        public static final MapCodec<WaterConverterRecipe> CODEC = RecordCodecBuilder.mapCodec(
                        inst -> inst.group(
                                Ingredient.CODEC.fieldOf("ingredient").forGetter(WaterConverterRecipe::inputItem),
                                ItemStack.CODEC.fieldOf("result").forGetter(WaterConverterRecipe::output)
                                )
                                .apply(inst, WaterConverterRecipe::new)
                );

        public static final PacketCodec<RegistryByteBuf, WaterConverterRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, WaterConverterRecipe::inputItem,
                        ItemStack.PACKET_CODEC, WaterConverterRecipe::output,
                        WaterConverterRecipe::new
                );

        @Override
        public MapCodec<WaterConverterRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, WaterConverterRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }

}
