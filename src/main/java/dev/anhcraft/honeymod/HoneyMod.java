package dev.anhcraft.honeymod;

import dev.anhcraft.honeymod.fluid.HoneyFluid;
import dev.anhcraft.honeymod.items.HoneyBucket;
import dev.anhcraft.honeymod.items.HoneyPie;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HoneyMod implements ModInitializer {
    public static final String NAMESPACE = "honeymod";
    private static HoneyMod instance;
    public HoneyBucket honeyBucket;
    public FluidBlock honeyFluidBlock;
    public HoneyFluid.Still honeyFluidStill;
    public HoneyFluid.Flowing honeyFluidFlowing;

    public static HoneyMod getInstance(){
        return instance;
    }

    @Override
    public void onInitialize() {
        instance = this;

        honeyFluidStill = new HoneyFluid.Still();
        honeyFluidFlowing = new HoneyFluid.Flowing();
        honeyBucket = new HoneyBucket(honeyFluidStill);
        honeyFluidBlock = Registry.BLOCK.add(new Identifier(NAMESPACE, "honey"), new HoneyFluid.Block(honeyFluidStill));

        Registry.register(Registry.FLUID, new Identifier(NAMESPACE, "honey_fluid"), honeyFluidStill);
        Registry.register(Registry.FLUID, new Identifier(NAMESPACE, "flowing_honey_fluid"), honeyFluidFlowing);
        Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "honey_bucket"), honeyBucket);
        Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "honey_pie"), new HoneyPie());
    }
}
