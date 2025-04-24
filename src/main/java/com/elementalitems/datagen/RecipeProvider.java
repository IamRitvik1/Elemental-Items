package com.elementalitems.datagen;

import com.elementalitems.ModBlocks;
import com.elementalitems.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected net.minecraft.data.recipe.RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new net.minecraft.data.recipe.RecipeGenerator(registryLookup,exporter) {
            @Override
            public void generate() {
                centerRecipe(ModItems.ELEMENTAL_SWORD,Items.NETHERITE_SWORD,ModItems.ELEMENTAL_SHARD,RecipeCategory.TOOLS);
                centerRecipe(ModItems.ELEMENTAL_AXE,Items.NETHERITE_AXE,ModItems.ELEMENTAL_SHARD,RecipeCategory.TOOLS);
                centerRecipe(ModItems.ELEMENTAL_PICKAXE,Items.NETHERITE_PICKAXE,ModItems.ELEMENTAL_SHARD,RecipeCategory.TOOLS);
                centerRecipe(ModItems.ELEMENTAL_SHOVEL,Items.NETHERITE_SHOVEL,ModItems.ELEMENTAL_SHARD,RecipeCategory.TOOLS);
                centerRecipe(ModItems.ELEMENTAL_HOE,Items.NETHERITE_HOE,ModItems.ELEMENTAL_SHARD,RecipeCategory.TOOLS);
                centerRecipe(ModBlocks.FIRE_CONVERTER.asItem(),ModBlocks.CONVERTER.asItem(),ModItems.FIRE_ESSENCE,RecipeCategory.MISC);
                centerRecipe(ModBlocks.WATER_CONVERTER.asItem(),ModBlocks.CONVERTER.asItem(),ModItems.WATER_ESSENCE,RecipeCategory.MISC);
                centerRecipe(ModBlocks.LIGHTNING_CONVERTER.asItem(),ModBlocks.CONVERTER.asItem(),ModItems.LIGHTNING_ESSENCE,RecipeCategory.MISC);
                offerStonecuttingRecipe(RecipeCategory.MISC,ModBlocks.CONVERTER.asItem(),Items.NETHERITE_BLOCK);
            }
            void centerRecipe(Item result, Item centerItem, Item surroundItem,RecipeCategory category){
                createShaped(category, result)
                        .pattern("eee").pattern("ece").pattern("eee")
                        .input('e',surroundItem)
                        .input('c',centerItem)
                        .criterion(hasItem(result),conditionsFromItem(result))
                        .offerTo(exporter)
                ;
            }
        };
    }

    @Override
    public String getName() {
        return "RecipeProvider";
    }
}
