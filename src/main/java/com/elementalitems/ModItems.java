package com.elementalitems;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.*;
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
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.FIRE_SHARD);
                    itemGroup.add(ModItems.WATER_SHARD);
                    itemGroup.add(ModItems.LIGHTNING_SHARD);
                    itemGroup.add(ModItems.ELEMENTAL_SHARD);
                    itemGroup.add(ModItems.FIRE_ESSENCE);
                    itemGroup.add(ModItems.WATER_ESSENCE);
                    itemGroup.add(ModItems.LIGHTNING_ESSENCE);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup)-> {
                    itemGroup.add(ModItems.ELEMENTAL_SWORD);
                    itemGroup.add(ModItems.ELEMENTAL_AXE);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup)-> {
                    itemGroup.add(ModItems.ELEMENTAL_PICKAXE);
                    itemGroup.add(ModItems.ELEMENTAL_AXE);
                    itemGroup.add(ModItems.ELEMENTAL_SHOVEL);
                    itemGroup.add(ModItems.ELEMENTAL_HOE);
                });
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

    public static final Item LIGHTNING_SHARD = register(
            "lightning_shard",
            LightningShard::new,
            new Item.Settings()
    );

    public static final Item ELEMENTAL_SHARD = register(
            "elemental_shard",
            Item::new,
            new Item.Settings()
    );

    public static final Item ELEMENTAL_SWORD = register("elemental_sword",
            settings -> new SwordItem(
            ElementalMaterial.ELEMENTAL_TOOL_MATERIAL,
            7,
            -2.4f,
            settings),
            new Item.Settings()
    );
    public static final Item ELEMENTAL_PICKAXE = register("elemental_pickaxe",
            settings -> new PickaxeItem(
                    ElementalMaterial.ELEMENTAL_TOOL_MATERIAL,
                    7,
                    -2.4f,
                    settings),
            new Item.Settings()
    );
    public static final Item ELEMENTAL_AXE = register("elemental_axe",
            settings -> new AxeItem(
                    ElementalMaterial.ELEMENTAL_TOOL_MATERIAL,
                    7,
                    -2.4f,
                    settings),
            new Item.Settings()
    );
    public static final Item ELEMENTAL_SHOVEL = register("elemental_shovel",
            settings -> new ShovelItem(
                    ElementalMaterial.ELEMENTAL_TOOL_MATERIAL,
                    7,
                    -2.4f,
                    settings),
            new Item.Settings()
    );
    public static final Item ELEMENTAL_HOE = register("elemental_hoe",
            settings -> new HoeItem(
                    ElementalMaterial.ELEMENTAL_TOOL_MATERIAL,
                    7,
                    -2.4f,
                    settings),
            new Item.Settings()
    );

    public static final Item FIRE_ESSENCE = register("fire_essence",Essence::new,new Item.Settings());
    public static final Item WATER_ESSENCE = register("water_essence",Essence::new,new Item.Settings());
    public static final Item LIGHTNING_ESSENCE = register("lightning_essence",Essence::new,new Item.Settings());

    public static Item register(String name, Function<Item.Settings,Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ElementalItems.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM,itemKey,item);

        return item;
    }
}
