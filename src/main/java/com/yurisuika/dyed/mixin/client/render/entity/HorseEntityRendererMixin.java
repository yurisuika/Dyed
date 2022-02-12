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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseEntityRenderer.class)
public class HorseEntityRendererMixin {

    private EntityRendererFactory.Context newContext;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/HorseEntityRenderer;addFeature(Lnet/minecraft/client/render/entity/feature/FeatureRenderer;)Z", ordinal = 0))
    private void injectInit(EntityRendererFactory.Context context, CallbackInfo ci) {
        newContext = context;
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/HorseEntityRenderer;addFeature(Lnet/minecraft/client/render/entity/feature/FeatureRenderer;)Z", ordinal = 1), index = 0)
    private FeatureRenderer modifyHorseArmorFeatureRenderer(FeatureRenderer renderer) {
        return new DyedHorseArmorFeatureRenderer((FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>>) this, newContext.getModelLoader());
    }

}
