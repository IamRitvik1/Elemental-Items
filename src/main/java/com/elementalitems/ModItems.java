package com.elementalitems;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static void initialize()
    {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItems.FIRE_SHARD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItems.WATER_SHARD));

        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(ModItems.FIRE_SHARD, 5 * 60 * 20));
    }

    public static final Item FIRE_SHARD = register(
            "fire_shard",
            FireShard::new,
            new Item.Settings()
    );

    public static final Item WATER_SHARD = register(
            "water_shard",
            WaterShard::new,
            new Item.Settings()
    );

    public static Item register(String name, Function<Item.Settings,Item> itemFactory, Item.Settings settings)
    {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ElementalItems.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM,itemKey,item);

        return item;
    }

    public static Item registerWithItem(String name,Item item)
    {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ElementalItems.MOD_ID, name));

        Registry.register(Registries.ITEM,itemKey,item);

        return item;
    }
}
