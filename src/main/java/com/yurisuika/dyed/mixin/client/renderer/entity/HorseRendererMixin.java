package com.yurisuika.dyed.mixin.client.renderer.entity;

import com.yurisuika.dyed.client.renderer.entity.layers.DyedLeatherHorseArmorLayer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.entity.passive.horse.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HorseRenderer.class)
public class HorseRendererMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/HorseRenderer;addLayer(Lnet/minecraft/client/renderer/entity/layers/LayerRenderer;)Z", ordinal = 1))
    private boolean redirectLayer(HorseRenderer instance, LayerRenderer layerRenderer) {
        return instance.addLayer(new DyedLeatherHorseArmorLayer((IEntityRenderer<HorseEntity, HorseModel<HorseEntity>>) this));
    }

}
