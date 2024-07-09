package dev.yurisuika.dyed.mixin.client.renderer.entity.layers;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HorseArmorLayer.class)
public interface HorseArmorLayerAccessor {

    @Accessor
    HorseModel<Horse> getModel();

}