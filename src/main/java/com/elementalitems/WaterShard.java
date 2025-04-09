package com.elementalitems;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.List;

public class WaterShard extends Item {
    public WaterShard(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            return ActionResult.PASS;
        }
        BlockPos blockPos = context.getBlockPos().offset(Direction.Axis.Y,1);
        BlockPos blockPosX = blockPos.offset(Direction.Axis.X, 1);
        BlockPos blockPosNX = blockPos.offset(Direction.Axis.X, -1);
        BlockPos blockPosZ = blockPos.offset(Direction.Axis.Z, 1);
        BlockPos blockPosNZ = blockPos.offset(Direction.Axis.Z, -1);



        context.getWorld().setBlockState(blockPos, Blocks.WATER.getDefaultState());
        context.getWorld().setBlockState(blockPosX, Blocks.WATER.getDefaultState());
        context.getWorld().setBlockState(blockPosNX, Blocks.WATER.getDefaultState());
        context.getWorld().setBlockState(blockPosZ, Blocks.WATER.getDefaultState());
        context.getWorld().setBlockState(blockPosNZ, Blocks.WATER.getDefaultState());
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemTooltip.elemental-items.water_shard").formatted(Formatting.BLUE));
    }
}
