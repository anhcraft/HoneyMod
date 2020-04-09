package dev.anhcraft.honeymod.items;

import dev.anhcraft.honeymod.fluid.HoneyFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;

public class HoneyBucket extends BucketItem {
    public HoneyBucket(HoneyFluid fluid) {
        super(fluid, new Settings().group(ItemGroup.MISC).recipeRemainder(Items.BUCKET).maxCount(1).rarity(Rarity.COMMON));
    }
}
