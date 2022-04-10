package com.yurisuika.dyed.mixin.client.render.entity;

import com.yurisuika.dyed.client.render.entity.feature.DyedHorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HorseEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HorseEntityRenderer.class)
public class HorseEntityRendererMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/HorseEntityRenderer;addFeature(Lnet/minecraft/client/render/entity/feature/FeatureRenderer;)Z", ordinal = 1))
    private boolean redirectFeature(HorseEntityRenderer instance, FeatureRenderer featureRenderer, EntityRendererFactory.Context context) {
        return instance.addFeature(new DyedHorseArmorFeatureRenderer((FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>>) this, context.getModelLoader()));
    }

}
