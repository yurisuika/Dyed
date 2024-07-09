package dev.yurisuika.dyed.mixin.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseArmorLayer.class)
public abstract class HorseArmorLayerMixin {

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HorseModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void renderDyedArmor(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Horse livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        ItemStack itemStack = livingEntity.getBodyArmorItem();
        if (itemStack.getItem() instanceof AnimalArmorItem animalArmorItem) {
            if (animalArmorItem.getBodyType() == AnimalArmorItem.BodyType.EQUESTRIAN) {
                ((HorseArmorLayer)(Object)this).getParentModel().copyPropertiesTo((((HorseArmorLayerAccessor)this).getModel()));
                ((HorseArmorLayerAccessor)this).getModel().prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
                ((HorseArmorLayerAccessor)this).getModel().setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                if (itemStack.is(ItemTags.DYEABLE)) {
                    renderLayer(poseStack, buffer, packedLight, animalArmorItem.getTexture(), ARGB32.opaque(DyedItemColor.getOrDefault(itemStack, -6265536)));
                    renderLayer(poseStack, buffer, packedLight, ResourceLocation.tryParse(animalArmorItem.getTexture().toString().replace(".png", "_overlay.png")), -1);
                } else {
                    renderLayer(poseStack, buffer, packedLight, animalArmorItem.getTexture(), -1);
                }
            }
        }
    }

    @Redirect(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HorseModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void removeVanillaRender(HorseModel<Horse> renderer, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {}

    @Unique
    private void renderLayer(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ResourceLocation resourceLocation, int color) {
        ((HorseArmorLayerAccessor)this).getModel().renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(resourceLocation)), packedLight, OverlayTexture.NO_OVERLAY, color);
    }

}