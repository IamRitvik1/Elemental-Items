package com.elementalitems;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class FireShard extends Item {
    public FireShard(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }

        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        boolean bool = false;

        if(!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
            blockPos = blockPos.offset(context.getSide());
            if (AbstractFireBlock.canPlaceAt(world, blockPos, context.getHorizontalPlayerFacing())) {
                this.playUseSound(context.getPlayer(), world, blockPos);
                world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, blockPos);
                bool = true;
            }
        } else {
            this.playUseSound(context.getPlayer(), world, blockPos);
            world.setBlockState(context.getBlockPos(), blockState.with(Properties.LIT, true));
            world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
            bool = true;
        }

        if(bool){
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        try{entity.setOnFireFor(100);} catch(Exception e){return ActionResult.PASS;}
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ActionResult.SUCCESS;
    }

    private void playUseSound(PlayerEntity player, World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemTooltip.elemental-items.fire_shard").formatted(Formatting.RED));
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}
