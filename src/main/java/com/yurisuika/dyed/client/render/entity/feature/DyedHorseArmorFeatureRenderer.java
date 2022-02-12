package com.yurisuika.dyed.client.render.entity.feature;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class DyedHorseArmorFeatureRenderer extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
    private final HorseEntityModel<HorseEntity> model;

    private static final Map<String, Identifier> HORSE_ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    public DyedHorseArmorFeatureRenderer(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new HorseEntityModel(loader.getModelPart(EntityModelLayers.HORSE_ARMOR));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, HorseEntity horseEntity, float f, float g, float h, float j, float k, float l) {
        this.renderHorseArmor(matrixStack, vertexConsumerProvider, i, horseEntity, f, g, h, j, k, l);
    }

    private void renderHorseArmor(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, HorseEntity horseEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = horseEntity.getArmorType();
        if (itemStack.getItem() instanceof HorseArmorItem horseArmorItem) {
            this.getContextModel().copyStateTo(this.model);
            this.model.animateModel(horseEntity, f, g, h);
            this.model.setAngles(horseEntity, f, g, j, k, l);
            if (horseArmorItem instanceof DyeableHorseArmorItem) {
                int i = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
                float n = (float)(i >> 16 & 255) / 255.0F;
                float o = (float)(i >> 8 & 255) / 255.0F;
                float p = (float)(i & 255) / 255.0F;
                this.renderHorseArmorParts(matrixStack, vertexConsumerProvider, light, horseArmorItem, n, o, p, null);
                this.renderHorseArmorParts(matrixStack, vertexConsumerProvider, light, horseArmorItem, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
                this.renderHorseArmorParts(matrixStack, vertexConsumerProvider, light, horseArmorItem, 1.0F, 1.0F, 1.0F, null);
            }
        }
    }

    private void renderHorseArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, HorseArmorItem name, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getHorseArmorTexture(name, overlay)));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    private Identifier getHorseArmorTexture(HorseArmorItem name, @Nullable String overlay) {
        String type = StringUtils.remove(String.valueOf(name), "_horse_armor");
        String string = "textures/entity/horse/armor/horse_armor_" + type + (overlay == null ? "" : "_" + overlay) + ".png";
        return HORSE_ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::new);
    }

}
