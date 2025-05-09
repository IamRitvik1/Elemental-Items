package com.elementalitems;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
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
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = context.getWorld().getBlockState(blockPos);
        try {
            context.getWorld().setBlockState(blockPos,blockState.with(Properties.WATERLOGGED,true));
        } catch (IllegalArgumentException e) {
            if (context.getWorld().getBlockState(blockPos.offset(Direction.Axis.Y,1)).equals(Blocks.AIR.getDefaultState())) {
                blockPos = blockPos.offset(Direction.Axis.Y,1);
                context.getWorld().setBlockState(blockPos, Blocks.WATER.getDefaultState());
            } else
            {
                return ActionResult.PASS;
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity.canFreeze())
        {
            entity.setFrozenTicks(100 * 20);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemTooltip.elemental-items.water_shard").formatted(Formatting.BLUE));
    }
}
