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

public record LightningConverterRecipe(Ingredient inputItem, ItemStack output) implements Recipe<PedestalRecipeInput> {

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
        return ModRecipes.LIGHTNING_CONVERTER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<PedestalRecipeInput>> getType() {
        return ModRecipes.LIGHTNING_CONVERTER_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(inputItem);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

       public static class Serializer implements RecipeSerializer<LightningConverterRecipe>{

        public static final MapCodec<LightningConverterRecipe> CODEC = RecordCodecBuilder.mapCodec(
                        inst -> inst.group(
                                Ingredient.CODEC.fieldOf("ingredient").forGetter(LightningConverterRecipe::inputItem),
                                ItemStack.CODEC.fieldOf("result").forGetter(LightningConverterRecipe::output)
                                )
                                .apply(inst, LightningConverterRecipe::new)
                );

        public static final PacketCodec<RegistryByteBuf, LightningConverterRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, LightningConverterRecipe::inputItem,
                        ItemStack.PACKET_CODEC, LightningConverterRecipe::output,
                        LightningConverterRecipe::new
                );

        @Override
        public MapCodec<LightningConverterRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, LightningConverterRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }

}
