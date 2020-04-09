package dev.anhcraft.honeymod.mixin;

import dev.anhcraft.honeymod.StrictHoneyState;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.fluid.FluidState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
    @ModifyVariable(
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 0,
                    shift = At.Shift.BY,
                    by = -3
            ),
            method = "render(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/world/ClientWorld;IF)V"
    )
    private static FluidState fluidState(FluidState fluidState){
        return new StrictHoneyState(fluidState);
    }
}
