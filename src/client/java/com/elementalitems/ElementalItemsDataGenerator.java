package com.elementalitems;

import com.elementalitems.datagen.BlockTagProvider;
import com.elementalitems.datagen.ItemTagProvider;
import com.elementalitems.datagen.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ElementalItemsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(ItemTagProvider::new);
		pack.addProvider(BlockTagProvider::new);
	}
}
