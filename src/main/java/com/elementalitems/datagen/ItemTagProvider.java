package com.elementalitems.datagen;

import com.elementalitems.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider<Item> {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.REPAIRS_ELEMENTAL_ARMOR).add(ModItems.ELEMENTAL_SHARD);
        getOrCreateTagBuilder(ModTags.SHARDS).add(Items.AMETHYST_SHARD).add(ModItems.ELEMENTAL_SHARD).add(ModItems.FIRE_SHARD).add(ModItems.WATER_SHARD).add(ModItems.LIGHTNING_SHARD);
    }
}
