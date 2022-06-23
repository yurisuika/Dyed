package com.yurisuika.dyed.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class DyedLeatherHorseArmorLayer extends LayerRenderer<HorseEntity, HorseModel<HorseEntity>> {
    private final HorseModel<HorseEntity> model = new HorseModel<>(0.1F);

    private static final Map<String, ResourceLocation> HORSE_ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    public DyedLeatherHorseArmorLayer(IEntityRenderer<HorseEntity, HorseModel<HorseEntity>> p_i50937_1_) {
        super(p_i50937_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, HorseEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        this.renderHorseArmor(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
    }

    private void renderHorseArmor(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, HorseEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        ItemStack itemstack = p_225628_4_.getArmor();
        if (itemstack.getItem() instanceof HorseArmorItem) {
            HorseArmorItem horsearmoritem = (HorseArmorItem)itemstack.getItem();
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_);
            this.model.setupAnim(p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
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
        model.renderToBuffer(p_225628_1_, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    private ResourceLocation getHorseArmorTexture(HorseArmorItem name, @Nullable String overlay) {
        String type = StringUtils.remove(String.valueOf(name.getTexture()), ".png");
        String string = type + (overlay == null ? "" : "_" + overlay) + ".png";
        return HORSE_ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ResourceLocation::new);
    }

}
