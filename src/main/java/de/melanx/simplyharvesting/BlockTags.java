package de.melanx.simplyharvesting;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider {

    public static final TagKey<Block> BERRY_BUSHES = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "berry_bushes"));
    public static final TagKey<Block> IGNORE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(SimplyHarvesting.MODID, "ignore_harvesting"));

    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.tag(IGNORE).addTag(BERRY_BUSHES);
        IntrinsicTagAppender<Block> berryBushesTag = this.tag(BERRY_BUSHES);

        berryBushesTag.add(Blocks.SWEET_BERRY_BUSH);

        // Autumnity
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("autumnity", "tall_foul_berry_bush"));

        // Oh The Biomes We've Gone
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("biomeswevegone", "blueberry_bush"));

        // Fruits Delight
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("fruitsdelight", "blueberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("fruitsdelight", "cranberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("fruitsdelight", "lemon_tree"));

        // Hearth and Harvest
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("hearthandharvest", "blueberry_bush"));

        // Kawaii Dishes
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("kawaiidishes", "coffee_bush"));

        // NewWorld
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("newworld", "blueberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("newworld", "cloudberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("newworld", "raspberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("newworld", "strawberry_bush"));

        // Not Enough Bush
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("notenoughbush", "galeberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("notenoughbush", "blackberry_bush"));

        // Skyrimcraft
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("skyrimcraft", "snowberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("skyrimcraft", "juniper_berry_bush"));

        // Twindling Tweaks
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("twindlingtweaks", "blueberry_bush"));
        berryBushesTag.addOptional(ResourceLocation.fromNamespaceAndPath("twindlingtweaks", "raspberry_bush"));
    }
}
