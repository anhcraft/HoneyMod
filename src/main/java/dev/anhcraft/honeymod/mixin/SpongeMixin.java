package dev.anhcraft.honeymod.mixin;

import dev.anhcraft.honeymod.HoneyMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.SpongeBlock;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Queue;

@Mixin(SpongeBlock.class)
public abstract class SpongeMixin {
    @Inject(
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            method = "absorbWater(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            cancellable = true
    )
    private void absorbWater(World world, BlockPos pos, CallbackInfoReturnable<Boolean> callback, Queue<Pair<BlockPos, Integer>> queue, int i, Pair<BlockPos, Integer> pair, BlockPos blockPos, int j, Direction[] var8, int var9, int var10, Direction direction, BlockPos blockPos2, BlockState blockState, FluidState fluidState, Material material) {
        if (var10 == 0 && HoneyMod.getInstance().honeyFluidStill.matchesType(fluidState.getFluid())) {
            List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, new Box(blockPos2).expand(8));
            for (BeeEntity bee : world.getNonSpectatingEntities(BeeEntity.class, new Box(blockPos2).expand(25))){
                if(players.isEmpty()) break;
                bee.setAttacker(players.remove(0));
            }
        }
    }
}
