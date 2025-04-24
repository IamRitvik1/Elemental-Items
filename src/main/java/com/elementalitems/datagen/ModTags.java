package com.elementalitems.datagen;

import com.elementalitems.ElementalItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Block> INCORRECT_FOR_ELEMENTAL_TOOL = TagKey.of(
            RegistryKeys.BLOCK,
            Identifier.of(ElementalItems.MOD_ID, "incorrect_for_elemental_tool")
    );
    public static final TagKey<Item> REPAIRS_ELEMENTAL_ARMOR = TagKey.of(
            RegistryKeys.ITEM,
            Identifier.of(ElementalItems.MOD_ID,"repairs_elemental_armor")
    );
    public static final TagKey<Item> SHARDS = TagKey.of(RegistryKeys.ITEM,Identifier.of(ElementalItems.MOD_ID,"shards"));
}
