package de.melanx.simplyharvesting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class EventListener {

    @SubscribeEvent
    public static void datagen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        event.addProvider(new BlockTags(generator.getPackOutput(), event.getLookupProvider(), SimplyHarvesting.MODID, event.getExistingFileHelper()));
    }

    @SubscribeEvent
    public static void onRightclickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockHitResult hitResult = event.getHitVec();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Block block = state.getBlock();
        Age age = block instanceof CropBlock crop ? new Age(crop.getAgeProperty(), crop.getMaxAge())
                : block instanceof CocoaBlock ? new Age(CocoaBlock.AGE, CocoaBlock.MAX_AGE) : null;

        if (state.is(BlockTags.BERRY_BUSHES) || (event.getEntity().isShiftKeyDown() && ServerConfig.disableOnSneaking())) {
            return;
        }

        if (age == null) {
            for (Property<?> property : state.getProperties()) {
                if (property instanceof IntegerProperty integerProperty && integerProperty.getName().equals("age")) {
                    age = new Age(integerProperty, integerProperty.max);
                    break;
                }
            }
        }

        if (age != null && state.getValue(age.property) == age.maxAge) {
            Level level = event.getLevel();
            Player player = event.getEntity();
            if (!level.isClientSide) {
                //noinspection ConstantConditions
                LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(block.getLootTable());
                ObjectArrayList<ItemStack> drops = lootTable.getRandomItems(new LootParams.Builder((ServerLevel) level)
                        .withParameter(LootContextParams.THIS_ENTITY, player)
                        .withParameter(LootContextParams.BLOCK_STATE, state)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                        .withParameter(LootContextParams.TOOL, player.getItemInHand(event.getHand()))
                        .create(LootContextParamSets.BLOCK));

                level.setBlock(pos, state.setValue(age.property, 0), Block.UPDATE_ALL);

                for (ItemStack drop : drops) {
                    if (drop.getItem() instanceof BlockItem) {
                        drop.shrink(1);
                    }

                    Block.popResource(level, pos, drop);
                }
            }

            SoundType soundType = block.getSoundType(state, level, pos, player);
            level.playSound(player, pos, soundType.getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.addDestroyBlockEffect(pos, state);

            UseOnContext useOnContext = new UseOnContext(player, event.getHand(), hitResult);
            event.getItemStack().onItemUseFirst(useOnContext);
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    private record Age(IntegerProperty property, int maxAge) {}
}
