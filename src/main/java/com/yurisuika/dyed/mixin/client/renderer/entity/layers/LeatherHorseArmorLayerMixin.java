package com.yurisuika.dyed.mixin.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mixin(LeatherHorseArmorLayer.class)
public class LeatherHorseArmorLayerMixin {

    private static final Map<String, ResourceLocation> HORSE_ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    @Inject(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/passive/horse/HorseEntity;FFFFFF)V", at = @At("TAIL"))
    public void injectRender(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, HorseEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_, CallbackInfo ci) {
        ItemStack itemstack = p_225628_4_.getArmor();
        if (itemstack.getItem() instanceof HorseArmorItem) {
            HorseArmorItem horsearmoritem = (HorseArmorItem)itemstack.getItem();
            ((LeatherHorseArmorLayer)(Object)this).getParentModel().copyPropertiesTo(((LeatherHorseArmorLayer)(Object)this).model);
            ((LeatherHorseArmorLayer)(Object)this).model.prepareMobModel(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_);
            ((LeatherHorseArmorLayer)(Object)this).model.setupAnim(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
            if (horsearmoritem instanceof DyeableHorseArmorItem) {
                int i = ((DyeableHorseArmorItem)horsearmoritem).getColor(itemstack);
                float n = (float)(i >> 16 & 255) / 255.0F;
                float o = (float)(i >> 8 & 255) / 255.0F;
                float p = (float)(i & 255) / 255.0F;
                this.renderHorseArmorParts(p_225628_1_, p_225628_2_, p_225628_3_, horsearmoritem, n, o, p, null);
                this.renderHorseArmorParts(p_225628_1_, p_225628_2_, p_225628_3_, horsearmoritem, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
                this.renderHorseArmorParts(p_225628_1_, p_225628_2_, p_225628_3_, horsearmoritem, 1.0F, 1.0F, 1.0F, null);
            }
        }
    }

    private void renderHorseArmorParts(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int light, HorseArmorItem name, float red, float green, float blue, @Nullable String overlay) {
        IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.armorCutoutNoCull(this.getHorseArmorTexture(name, overlay)));
        ((LeatherHorseArmorLayer)(Object)this).model.renderToBuffer(p_225628_1_, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    private ResourceLocation getHorseArmorTexture(HorseArmorItem name, @Nullable String overlay) {
        String type = StringUtils.remove(String.valueOf(name.getTexture()), ".png");
        String string = type + (overlay == null ? "" : "_" + overlay) + ".png";
        return HORSE_ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ResourceLocation::new);
    }

}
