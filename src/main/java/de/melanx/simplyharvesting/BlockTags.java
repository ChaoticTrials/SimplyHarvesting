package de.melanx.simplyharvesting;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider {

    public static final TagKey<Block> BERRY_BUSHES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", "berry_bushes"));
    public static final TagKey<Block> IGNORE = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SimplyHarvesting.MODID, "ignore_harvesting"));

    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.tag(IGNORE).addTag(BERRY_BUSHES);
        var berryBushesTag = this.tag(BERRY_BUSHES);

        berryBushesTag.add(BlockItemIds.SWEET_BERRY_CROP.block());

        // Autumnity
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("autumnity", "tall_foul_berry_bush")));

        // Oh The Biomes We've Gone
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("biomeswevegone", "blueberry_bush")));

        // Fruits Delight
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("fruitsdelight", "blueberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("fruitsdelight", "cranberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("fruitsdelight", "lemon_tree")));

        // Hearth and Harvest
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("hearthandharvest", "blueberry_bush")));

        // Kawaii Dishes
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("kawaiidishes", "coffee_bush")));

        // NewWorld
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("newworld", "blueberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("newworld", "cloudberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("newworld", "raspberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("newworld", "strawberry_bush")));

        // Not Enough Bush
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("notenoughbush", "galeberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("notenoughbush", "blackberry_bush")));

        // Skyrimcraft
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("skyrimcraft", "snowberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("skyrimcraft", "juniper_berry_bush")));

        // Twindling Tweaks
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("twindlingtweaks", "blueberry_bush")));
        berryBushesTag.add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("twindlingtweaks", "raspberry_bush")));
    }
}
