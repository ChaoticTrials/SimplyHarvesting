package de.melanx.simplyharvesting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
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
        if (state.getBlock() instanceof CropBlock crop && state.getValue(crop.getAgeProperty()) == crop.getMaxAge()) {
            Level level = event.getLevel();
            if (!level.isClientSide) {
                //noinspection ConstantConditions
                LootTable lootTable = level.getServer().getLootData().getLootTable(crop.getLootTable());
                ObjectArrayList<ItemStack> drops = lootTable.getRandomItems(new LootParams.Builder((ServerLevel) level)
                        .withParameter(LootContextParams.THIS_ENTITY, event.getEntity())
                        .withParameter(LootContextParams.BLOCK_STATE, state)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                        .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                        .create(LootContextParamSets.BLOCK));

                level.setBlock(pos, state.setValue(crop.getAgeProperty(), 0), Block.UPDATE_ALL);

                for (ItemStack drop : drops) {
                    if (drop.getItem() instanceof BlockItem) {
                        drop.shrink(1);
                    }

                    Block.popResource(level, pos, drop);
                }
            }

            SoundType soundType = crop.getSoundType(state, level, pos, event.getEntity());
            level.playSound(event.getEntity(), pos, soundType.getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.addDestroyBlockEffect(pos, state);

            UseOnContext useOnContext = new UseOnContext(event.getEntity(), event.getHand(), hitResult);
            event.getItemStack().onItemUseFirst(useOnContext);
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }
}
