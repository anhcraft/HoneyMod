package dev.anhcraft.honeymod.items;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;

public class HoneyPie extends Item {
    public HoneyPie() {
        super(new Item.Settings().group(ItemGroup.FOOD).rarity(Rarity.COMMON).food(new FoodComponent.Builder().hunger(8).saturationModifier(0.4f).build()));
    }
}
