package com.yurisuika.dyed.mixin.client.render.entity.feature;

import com.google.common.collect.Maps;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(HorseArmorFeatureRenderer.class)
public class HorseArmorFeatureRendererMixin {
    
    private static final Map<String, Identifier> HORSE_ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/HorseEntity;FFFFFF)V", at = @At("TAIL"))
    public void injectRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, HorseEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        ItemStack itemStack = entity.getArmorType();
        if (itemStack.getItem() instanceof HorseArmorItem horseArmorItem) {
            ((HorseArmorFeatureRenderer)(Object)this).getContextModel().copyStateTo(((HorseArmorFeatureRenderer)(Object)this).model);
            ((HorseArmorFeatureRenderer)(Object)this).model.animateModel(entity, limbAngle, limbDistance, tickDelta);
            ((HorseArmorFeatureRenderer)(Object)this).model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            if (horseArmorItem instanceof DyeableHorseArmorItem) {
                int i = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
                float n = (float)(i >> 16 & 255) / 255.0F;
                float o = (float)(i >> 8 & 255) / 255.0F;
                float p = (float)(i & 255) / 255.0F;
                this.renderHorseArmorParts(matrices, vertexConsumers, light, horseArmorItem, n, o, p, null);
                this.renderHorseArmorParts(matrices, vertexConsumers, light, horseArmorItem, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
                this.renderHorseArmorParts(matrices, vertexConsumers, light, horseArmorItem, 1.0F, 1.0F, 1.0F, null);
            }
        }
    }

    private void renderHorseArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, HorseArmorItem name, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getHorseArmorTexture(name, overlay)));
        ((HorseArmorFeatureRenderer)(Object)this).model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    private Identifier getHorseArmorTexture(HorseArmorItem name, @Nullable String overlay) {
        String type = StringUtils.remove(String.valueOf(name.getEntityTexture()), ".png");
        String string = type + (overlay == null ? "" : "_" + overlay) + ".png";
        return HORSE_ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::new);
    }

}