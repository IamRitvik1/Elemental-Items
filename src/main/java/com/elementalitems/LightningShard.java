package com.elementalitems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class LightningShard extends Item {

    public LightningShard(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            return ActionResult.PASS;
        }
        BlockPos blockPos = context.getBlockPos();
        for (int i = 0; i < context.getStack().getCount()*2; i++) {
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT,context.getWorld());
            lightningEntity.setPosition(blockPos.toCenterPos());
            context.getWorld().spawnEntity(lightningEntity);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        BlockPos blockPos = entity.getBlockPos();
        for (int i = 0; i < stack.getCount()*2; i++) {
            LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT,user.getWorld());
            lightningEntity.setPosition(blockPos.toCenterPos());
            user.getWorld().spawnEntity(lightningEntity);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("itemTooltip.elemental-items.lightning_shard").formatted(Formatting.YELLOW));
    }
}
