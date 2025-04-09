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

import java.util.List;

public class FireShard extends Item {
    public FireShard(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            return ActionResult.PASS;
        }
        BlockPos blockPos = context.getBlockPos();

        context.getWorld().removeBlock(blockPos,false);
        context.getWorld().setBlockState(context.getBlockPos(), Blocks.FIRE.getDefaultState());
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemTooltip.elemental-items.fire_shard").formatted(Formatting.RED));
    }
}
