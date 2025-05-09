package com.elementalitems;

import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModBlockEntities.initialize();
        ModRecipes.initialize();
        ModScreenHandlers.initialize();
        ModEntities.initialize();
    }
}
