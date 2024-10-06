package dev.yurisuika.dyed.mixin.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(EquipmentLayerRenderer.class)
public abstract class EquipmentLayerRendererMixin {

    @ModifyVariable(method = "renderLayers(Lnet/minecraft/world/item/equipment/EquipmentModel$LayerType;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Function;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V", at = @At("STORE"), ordinal = 0)
    private List<EquipmentModel.Layer> modvar(List<EquipmentModel.Layer> list, EquipmentModel.LayerType layerType, ResourceLocation resourceLocation) {
        if (layerType.equals(EquipmentModel.LayerType.HORSE_BODY) && resourceLocation.equals(ResourceLocation.withDefaultNamespace("leather"))) {
            return List.of(EquipmentModel.Layer.leatherDyeable(ResourceLocation.withDefaultNamespace("leather"), true), EquipmentModel.Layer.leatherDyeable(ResourceLocation.withDefaultNamespace("leather_overlay"), false));
        }
        return list;
    }

}