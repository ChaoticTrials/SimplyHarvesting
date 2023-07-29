package de.melanx.simplyharvesting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListener {

    @SubscribeEvent
    public void onRightclickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockHitResult hitResult = event.getHitVec();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Block block = state.getBlock();
        Age age = block instanceof CropBlock crop ? new Age(crop.getAgeProperty(), crop.getMaxAge())
                : block instanceof CocoaBlock ? new Age(CocoaBlock.AGE, CocoaBlock.MAX_AGE) : null;

        if (age != null && state.getValue(age.property) == age.maxAge) {
            Level level = event.getLevel();
            if (!level.isClientSide) {
                //noinspection ConstantConditions
                LootTable lootTable = level.getServer().getLootTables().get(block.getLootTable());
                ObjectArrayList<ItemStack> drops = lootTable.getRandomItems(new LootContext.Builder((ServerLevel) level)
                        .withParameter(LootContextParams.THIS_ENTITY, event.getEntity())
                        .withParameter(LootContextParams.BLOCK_STATE, state)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                        .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                        .create(LootContextParamSets.BLOCK));

                level.setBlock(pos, state.setValue(age.property, 0), Block.UPDATE_ALL);

                for (ItemStack drop : drops) {
                    if (drop.getItem() instanceof BlockItem) {
                        drop.shrink(1);
                    }

                    Block.popResource(level, pos, drop);
                }
            }

            SoundType soundType = block.getSoundType(state, level, pos, event.getEntity());
            level.playSound(event.getEntity(), pos, soundType.getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.addDestroyBlockEffect(pos, state);
        }
    }

    private record Age(IntegerProperty property, int maxAge) {}
}
