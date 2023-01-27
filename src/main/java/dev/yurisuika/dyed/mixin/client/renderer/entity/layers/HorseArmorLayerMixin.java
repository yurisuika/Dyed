package dev.yurisuika.dyed.mixin.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mixin(HorseArmorLayer.class)
public class HorseArmorLayerMixin {

    private static final Map<String, ResourceLocation> HORSE_ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V", at = @At("TAIL"))
    public void injectRender(PoseStack p_117032_, MultiBufferSource p_117033_, int p_117034_, Horse p_117035_, float p_117036_, float p_117037_, float p_117038_, float p_117039_, float p_117040_, float p_117041_, CallbackInfo ci) {
        ItemStack itemStack = p_117035_.getArmor();
        if (itemStack.getItem() instanceof HorseArmorItem horseArmorItem) {
            ((HorseArmorLayer)(Object)this).getParentModel().copyPropertiesTo(((HorseArmorLayer)(Object)this).model);
            ((HorseArmorLayer)(Object)this).model.prepareMobModel(p_117035_, p_117036_, p_117037_, p_117038_);
            ((HorseArmorLayer)(Object)this).model.setupAnim(p_117035_, p_117036_, p_117037_, p_117039_, p_117040_, p_117041_);
            if (horseArmorItem instanceof DyeableHorseArmorItem) {
                int i = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
                float n = (float)(i >> 16 & 255) / 255.0F;
                float o = (float)(i >> 8 & 255) / 255.0F;
                float p = (float)(i & 255) / 255.0F;
                this.renderHorseArmorParts(p_117032_, p_117033_, p_117034_, horseArmorItem, n, o, p, null);
                this.renderHorseArmorParts(p_117032_, p_117033_, p_117034_, horseArmorItem, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
                this.renderHorseArmorParts(p_117032_, p_117033_, p_117034_, horseArmorItem, 1.0F, 1.0F, 1.0F, null);
            }
        }
    }

    private void renderHorseArmorParts(PoseStack p_117119_, MultiBufferSource p_117120_, int light, HorseArmorItem name, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = p_117120_.getBuffer(RenderType.armorCutoutNoCull(this.getHorseArmorTexture(name, overlay)));
        ((HorseArmorLayer)(Object)this).model.renderToBuffer(p_117119_, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    private ResourceLocation getHorseArmorTexture(HorseArmorItem name, @Nullable String overlay) {
        String type = StringUtils.remove(String.valueOf(name.getTexture()), ".png");
        String string = type + (overlay == null ? "" : "_" + overlay) + ".png";
        return HORSE_ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ResourceLocation::new);
    }

}