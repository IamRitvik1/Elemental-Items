package com.elementalitems;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void initialize(){}

    public static final RecipeSerializer<FireConverterRecipe> FIRE_CONVERTER_RECIPE_SERIALIZER = registerSerializer("fire_converter",new FireConverterRecipe.Serializer());
    public static final RecipeType<FireConverterRecipe> FIRE_CONVERTER_RECIPE_TYPE = register("fire_converter");
    public static final RecipeType<WaterConverterRecipe> WATER_CONVERTER_RECIPE_TYPE = register("water_converter");
    public static final RecipeSerializer<WaterConverterRecipe> WATER_CONVERTER_RECIPE_SERIALIZER = registerSerializer("water_converter",new WaterConverterRecipe.Serializer());
    public static final RecipeType<LightningConverterRecipe> LIGHTNING_CONVERTER_RECIPE_TYPE = register("lightning_converter");
    public static final RecipeSerializer<LightningConverterRecipe> LIGHTNING_CONVERTER_RECIPE_SERIALIZER = registerSerializer("lightning_converter",new LightningConverterRecipe.Serializer());
    private static <T extends Recipe<? extends RecipeInput>> RecipeType<T> register(String name){
        return Registry.register(
                Registries.RECIPE_TYPE, Identifier.of(ElementalItems.MOD_ID, name), new RecipeType<T>() {
                    @Override
                    public String toString() {
                        return name;
                    }
                });
    }
    private static <T extends Recipe<? extends RecipeInput>> RecipeSerializer<T> registerSerializer(String name, RecipeSerializer<T> serializer){
        return Registry.register(
                Registries.RECIPE_SERIALIZER, Identifier.of(ElementalItems.MOD_ID, name),
               serializer);
    }
}
