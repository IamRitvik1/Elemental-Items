package com.elementalitems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class ElementalItemsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockEntityRendererFactories.register(ModBlockEntities.ELEMENTAL_BLOCK_ENTITY, ElementalBlockEntityRenderer::new);
		HandledScreens.register(ModScreenHandlers.CONVERTER_SCREEN_HANDLER, ConverterScreen::new);
	}
}