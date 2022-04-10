package com.yurisuika.dyed.mixin.client.renderer.entity;

import com.yurisuika.dyed.client.renderer.entity.feature.DyedHorseArmorLayer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HorseRenderer.class)
public class HorseRendererMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/HorseRenderer;addLayer(Lnet/minecraft/client/renderer/entity/layers/RenderLayer;)Z", ordinal = 1))
    private boolean redirectLayer(HorseRenderer instance, RenderLayer renderLayer, EntityRendererProvider.Context p_174167_) {
        return instance.addLayer(new DyedHorseArmorLayer((RenderLayerParent<Horse, HorseModel<Horse>>) this, p_174167_.getModelSet()));
    }

}
