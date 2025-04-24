package com.elementalitems;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;

public class ElementalBlockEntityRenderer implements BlockEntityRenderer<ElementalBlockEntity> {
    ItemRenderer itemRenderer;
    public ElementalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }
    @Override
    public void render(ElementalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = this.itemRenderer;
        for(int i = 0; i < 2; i++){
            float z = i == 0 ?-0.005f : 1.005f;
            if (entity.contains(ModItems.FIRE_SHARD)) {
                matrices.push();
                ItemStack stack = new ItemStack(ModItems.FIRE_SHARD);

                matrices.translate(0.5, 0.5f, z);
                matrices.scale(0.5f, 0.5f, 0.5f);

                itemRenderer.renderItem(stack, ModelTransformationMode.GUI, 255, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                matrices.pop();
            }
            if (entity.contains(ModItems.WATER_SHARD)) {
                matrices.push();
                ItemStack stack = new ItemStack(ModItems.WATER_SHARD);

                matrices.translate(0.2f, 0.5f, z);
                matrices.scale(0.5f, 0.5f, 0.5f);

                itemRenderer.renderItem(stack, ModelTransformationMode.GUI, 255, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                matrices.pop();
            }
            if (entity.contains(ModItems.LIGHTNING_SHARD)) {
                matrices.push();
                ItemStack stack = new ItemStack(ModItems.LIGHTNING_SHARD);

                matrices.translate(0.8, 0.5f, z);
                matrices.scale(0.5f, 0.5f, 0.5f);

                itemRenderer.renderItem(stack, ModelTransformationMode.GUI, 255, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                matrices.pop();
            }
        }
    }
}
