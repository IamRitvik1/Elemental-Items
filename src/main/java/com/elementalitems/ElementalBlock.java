package com.elementalitems;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ElementalBlock extends BlockWithEntity implements BlockEntityProvider {

    public ElementalBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(ElementalBlock::new);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

        if(entity instanceof ItemEntity itemEntity) {
            if(itemEntity.getStack().getItem() == ModItems.FIRE_SHARD) {
                itemEntity.setStack(new ItemStack(Items.DIAMOND, itemEntity.getStack().getCount()));
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ElementalBlockEntity(pos,state);
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof ElementalBlockEntity) {
                ItemScatterer.spawn(world, pos, ((ElementalBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                         PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof ElementalBlockEntity elementalBlockEntity) {
            boolean hasFire = elementalBlockEntity.contains(ModItems.FIRE_SHARD) && player.getMainHandStack().isOf(ModItems.FIRE_SHARD);
            boolean hasWater = elementalBlockEntity.contains(ModItems.WATER_SHARD) && player.getMainHandStack().isOf(ModItems.WATER_SHARD);
            boolean hasLightning = elementalBlockEntity.contains(ModItems.LIGHTNING_SHARD) && player.getMainHandStack().isOf(ModItems.LIGHTNING_SHARD);
            boolean isShard = player.getMainHandStack().isOf(ModItems.LIGHTNING_SHARD) || player.getMainHandStack().isOf(ModItems.WATER_SHARD) || player.getMainHandStack().isOf(ModItems.FIRE_SHARD);
            if (!(elementalBlockEntity.getAvailableSlots(Direction.DOWN).length == 0) && !stack.isEmpty() && !hasFire && !hasWater && !hasLightning) {
                if (isShard) {
                    elementalBlockEntity.setStack(Arrays.stream(elementalBlockEntity.getAvailableSlots(Direction.DOWN)).min().orElse(1), stack.copyWithCount(1));
                    world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                    stack.decrement(1);
                    world.updateListeners(pos, state, state, 0);
                }
            } else if (stack.isEmpty() && !player.isSneaking()) {
                for (int i = 0; i < elementalBlockEntity.size(); i++) {
                    try {
                        ItemStack itemStack = elementalBlockEntity.getStack(i);
                        player.giveItemStack(itemStack);
                        world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                    } catch (Exception e) {
                        break;
                    }
                }
                elementalBlockEntity.clear();

                elementalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
            hasFire = elementalBlockEntity.contains(ModItems.FIRE_SHARD);
            hasWater = elementalBlockEntity.contains(ModItems.WATER_SHARD);
            hasLightning = elementalBlockEntity.contains(ModItems.LIGHTNING_SHARD);
            if (hasFire && hasWater && hasLightning) {
                ItemStack itemStack = new ItemStack(ModItems.ELEMENTAL_SHARD);
                player.giveItemStack(itemStack);
                elementalBlockEntity.clear();

                elementalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
        }
        return ActionResult.SUCCESS;
    }

    public static int getLuminance(BlockState currentBlockState){
        return 15;
    }
}
