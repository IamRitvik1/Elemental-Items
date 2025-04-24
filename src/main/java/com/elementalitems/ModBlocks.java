package com.elementalitems;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks{
    public static void initialize()
    {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
                .register((itemGroup) -> itemGroup.add(ModBlocks.ELEMENTAL_BLOCK.asItem()));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> {
            itemGroup.add(ModBlocks.FIRE_CONVERTER.asItem());
            itemGroup.add(ModBlocks.WATER_CONVERTER.asItem());
            itemGroup.add(ModBlocks.LIGHTNING_CONVERTER.asItem());
            itemGroup.add(ModBlocks.CONVERTER.asItem());
        });
    }
    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ElementalItems.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ElementalItems.MOD_ID, name));
    }

    public static final Block ELEMENTAL_BLOCK = register(
            "elemental_block",
            ElementalBlock::new,
            AbstractBlock.Settings.create().luminance(ElementalBlock::getLuminance),
            true);
    public static final Block FIRE_CONVERTER = register(
            "fire_converter",
            FireConverterBlock::new,
            AbstractBlock.Settings.create(),
            true
    );
    public static final Block WATER_CONVERTER = register(
            "water_converter",
            WaterConverterBlock::new,
            AbstractBlock.Settings.create(),
            true
    );
    public static final Block LIGHTNING_CONVERTER = register(
            "lightning_converter",
            LightningConverterBlock::new,
            AbstractBlock.Settings.create(),
            true
    );
    public static final Block CONVERTER = register(
            "converter",
            ConverterBlock::new,
            AbstractBlock.Settings.create(),
            true
    );

}
