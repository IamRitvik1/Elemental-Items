package com.elementalitems;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static void initialize(){}
    public static final ScreenHandlerType<ConverterScreenHandler> CONVERTER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(ElementalItems.MOD_ID, "converter_screen_handler"),
            new ExtendedScreenHandlerType<>(ConverterScreenHandler::new, BlockPos.PACKET_CODEC));
}
