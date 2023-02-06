package dev.yurisuika.dyed.mixin.client.render.entity.feature;

import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HorseArmorFeatureRenderer.class)
public interface HorseArmorFeatureRendererAccessor {

    @Accessor
    static HorseEntityModel<HorseEntity> getModel() {
        throw new AssertionError();
    }

}