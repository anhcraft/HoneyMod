package dev.anhcraft.honeymod.fluid;

import dev.anhcraft.honeymod.HoneyMod;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class HoneyFluid extends BaseFluid {
    @Override
    public Fluid getFlowing() {
        return HoneyMod.getInstance().honeyFluidFlowing;
    }

    @Override
    public Fluid getStill() {
        return HoneyMod.getInstance().honeyFluidStill;
    }

    @Override
    public Item getBucketItem() {
        return HoneyMod.getInstance().honeyBucket;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return state.getHeight(world, pos) >= 0.44444445F && fluid.isIn(FluidTags.WATER);
    }

    @Override
    public void randomDisplayTick(World world, BlockPos blockPos, FluidState fluidState, Random random) {
        if (fluidState.isStill() && fluidState.get(FALLING)) {
            world.addParticle(ParticleTypes.FALLING_HONEY, blockPos.getX() + random.nextFloat(), blockPos.getY() + random.nextFloat(), blockPos.getZ() + random.nextFloat(), 0D, 0D, 0D);
        }
    }

    @Override
    public ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_HONEY;
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(IWorld world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.getBlock().hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world.getWorld(), pos, blockEntity);
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return HoneyMod.getInstance().honeyFluidBlock.getDefaultState().with(FluidBlock.LEVEL, method_15741(fluidState));
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == HoneyMod.getInstance().honeyFluidStill || fluid == HoneyMod.getInstance().honeyFluidFlowing;
    }

    @Override
    public boolean isIn(Tag<Fluid> tag) {
        return tag != FluidTags.LAVA;
    }

    @Override
    public int getLevelDecreasePerBlock(WorldView view) {
        return 1;
    }

    @Override
    public int getTickRate(WorldView worldView) {
        return 5;
    }

    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    public static class Flowing extends HoneyFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends HoneyFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }

    public static class Block extends FluidBlock {
        public Block(BaseFluid fluid) {
            super(fluid, Block.Settings.copy(Blocks.WATER).jumpVelocityMultiplier(0.4f).velocityMultiplier(0.9f));
        }

        @Override
        public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
            return true;
        }

        @Override
        public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
            if(entity instanceof LivingEntity && !state.getFluidState().isStill() && world.random.nextInt(100) <= 1) {
                world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_HONEY_BLOCK_STEP, SoundCategory.BLOCKS, world.random.nextFloat() * 0.25F + 0.15F, world.random.nextFloat() + 0.5F, false);
            }
        }
    }
}
