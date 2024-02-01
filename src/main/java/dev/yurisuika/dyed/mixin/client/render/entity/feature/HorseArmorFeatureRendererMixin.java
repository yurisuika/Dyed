package dev.yurisuika.dyed.mixin.client.render.entity.feature;

import com.google.common.collect.Maps;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
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
        ItemStack itemStack = entity.getBodyArmor();
        if (itemStack.getItem() instanceof AnimalArmorItem animalArmorItem) {
            if (animalArmorItem.getType() == AnimalArmorItem.Type.EQUESTRIAN) {
                ((HorseArmorFeatureRenderer)(Object)this).getContextModel().copyStateTo((((HorseArmorFeatureRendererAccessor)this).getModel()));
                ((HorseArmorFeatureRendererAccessor)this).getModel().animateModel(entity, limbAngle, limbDistance, tickDelta);
                ((HorseArmorFeatureRendererAccessor)this).getModel().setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                if (itemStack.isIn(ItemTags.DYEABLE)) {
                    int i = DyeableItem.getColor(itemStack);
                    float n = (float)(i >> 16 & 255) / 255.0F;
                    float o = (float)(i >> 8 & 255) / 255.0F;
                    float p = (float)(i & 255) / 255.0F;
                    this.renderHorseArmorParts(matrices, vertexConsumers, light, animalArmorItem, n, o, p, null);
                    this.renderHorseArmorParts(matrices, vertexConsumers, light, animalArmorItem, 1.0F, 1.0F, 1.0F, "overlay");
                } else {
                    this.renderHorseArmorParts(matrices, vertexConsumers, light, animalArmorItem, 1.0F, 1.0F, 1.0F, null);
                }
            }
        }
    }

    private void renderHorseArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, AnimalArmorItem name, float red, float green, float blue, @Nullable String overlay) {
        ((HorseArmorFeatureRendererAccessor)this).getModel().render(matrices, vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getHorseArmorTexture(name, overlay))), light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    private Identifier getHorseArmorTexture(AnimalArmorItem name, @Nullable String overlay) {
        return HORSE_ARMOR_TEXTURE_CACHE.computeIfAbsent(StringUtils.remove(String.valueOf(name.getEntityTexture()), ".png") + (overlay == null ? "" : "_" + overlay) + ".png", Identifier::new);
    }

}