package dev.yurisuika.dyed.mixin.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseArmorFeatureRenderer.class)
public abstract class HorseArmorFeatureRendererMixin {

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/HorseEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/HorseEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void renderDyedArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, HorseEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        ItemStack itemStack = entity.getBodyArmor();
        if (itemStack.getItem() instanceof AnimalArmorItem animalArmorItem) {
            if (animalArmorItem.getType() == AnimalArmorItem.Type.EQUESTRIAN) {
                ((HorseArmorFeatureRenderer)(Object)this).getContextModel().copyStateTo(((HorseArmorFeatureRendererAccessor)this).getModel());
                ((HorseArmorFeatureRendererAccessor)this).getModel().animateModel(entity, limbAngle, limbDistance, tickDelta);
                ((HorseArmorFeatureRendererAccessor)this).getModel().setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                if (itemStack.isIn(ItemTags.DYEABLE)) {
                    int color = DyedColorComponent.getColor(itemStack, -6265536);
                    render(matrices, vertexConsumers, light, animalArmorItem.getEntityTexture(), (float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F);
                    render(matrices, vertexConsumers, light, Identifier.tryParse(animalArmorItem.getEntityTexture().toString().replace(".png", "_overlay.png")), 1.0F, 1.0F, 1.0F);
                } else {
                    render(matrices, vertexConsumers, light, animalArmorItem.getEntityTexture(), 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/HorseEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/HorseEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void removeVanillaRender(HorseEntityModel instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {}

    @Unique
    private void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Identifier identifier, float red, float green, float blue) {
        ((HorseArmorFeatureRendererAccessor)this).getModel().render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(identifier)), light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

}