package com.elementalitems;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.of(ElementalItems.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static final BlockEntityType<ElementalBlockEntity> ELEMENTAL_BLOCK_ENTITY =
            register("elemental_block_entity", ElementalBlockEntity::new, ModBlocks.ELEMENTAL_BLOCK);
    public static final BlockEntityType<FireConverterBlockEntity> FIRE_PEDESTAL_BLOCK_ENTITY = register("fire_converter_block_entity", FireConverterBlockEntity::new,ModBlocks.FIRE_CONVERTER);
    public static final BlockEntityType<WaterConverterBlockEntity> WATER_PEDESTAL_BLOCK_ENTITY = register("water_converter_block_entity", WaterConverterBlockEntity::new,ModBlocks.WATER_CONVERTER);
    public static final BlockEntityType<LightningConverterBlockEntity> LIGHTNING_PEDESTAL_BLOCK_ENTITY = register("lightning_converter_block_entity", LightningConverterBlockEntity::new,ModBlocks.LIGHTNING_CONVERTER);
    public static void initialize() {}
}
