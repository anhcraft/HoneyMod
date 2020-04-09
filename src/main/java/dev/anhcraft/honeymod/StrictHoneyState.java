package dev.anhcraft.honeymod;

import dev.anhcraft.honeymod.fluid.HoneyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.FluidStateImpl;
import net.minecraft.tag.Tag;

public class StrictHoneyState extends FluidStateImpl {
    public StrictHoneyState(FluidState state) {
        super(state.getFluid(), state.getEntries());
    }

    @Override
    public boolean matches(Tag<Fluid> tag) {
        if(owner instanceof HoneyFluid){
            return false; // there is no "honey" tag currently, so always returns false
        } else {
            return owner.matches(tag);
        }
    }
}
