package com.elementalitems;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class FireConverterBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2,ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public FireConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_PEDESTAL_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch(index){
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index){
                    case 0: FireConverterBlockEntity.this.progress = value;
                    case 1: FireConverterBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt,inventory,registries);
        nbt.putInt("fire_converter.progress", progress);
        nbt.putInt("fire_converter.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.readNbt(nbt,inventory,registries);
        progress = nbt.getInt("fire_converter.progress");
        maxProgress = nbt.getInt("fire_converter.max_progress");
        super.readNbt(nbt, registries);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.elemental-items.fire_converter");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ConverterScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    public void tick(World world, BlockPos blockPos,BlockState blockState){
        if(hasRecipe()){
            increaseCraftingProgress();
            markDirty(world,blockPos,blockState);
            if(hasCraftingFinished()){
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
    }

    private void craftItem(){
        Optional<RecipeEntry<FireConverterRecipe>> recipe = getCurrentRecipe();

        ItemStack output = recipe.get().value().output();
        this.removeStack(INPUT_SLOT,1);
        this.setStack(OUTPUT_SLOT,new ItemStack(output.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished(){
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress(){
        this.progress++;
    }

    private boolean hasRecipe(){
        Optional<RecipeEntry<FireConverterRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()){
            return false;
        }
        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeEntry<FireConverterRecipe>> getCurrentRecipe(){
        return ((ServerWorld) Objects.requireNonNull(this.getWorld())).getRecipeManager().getFirstMatch(ModRecipes.FIRE_CONVERTER_RECIPE_TYPE,new PedestalRecipeInput(inventory.get(INPUT_SLOT)), this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output){
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count){
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();


        return maxCount >= currentCount + count;
    }
}
