package dev.anhcraft.honeymod.mixin;

import dev.anhcraft.honeymod.HoneyMod;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(GlassBottleItem.class)
public abstract class BottleMixin {
    @Shadow
    protected abstract ItemStack fill(ItemStack emptyBottle, PlayerEntity player, ItemStack filledBottle);

    @Inject(
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            cancellable = true
    )
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> callback, List<AreaEffectCloudEntity> list, ItemStack itemStack, HitResult hitResult, BlockPos blockPos) {
        if (HoneyMod.getInstance().honeyFluidStill.matchesType(world.getFluidState(blockPos).getFluid())) {
            world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1f, 1f);
            ItemStack item = new ItemStack(Items.HONEY_BOTTLE);
            callback.setReturnValue(TypedActionResult.success(fill(itemStack, user, item)));
        }
    }
}
