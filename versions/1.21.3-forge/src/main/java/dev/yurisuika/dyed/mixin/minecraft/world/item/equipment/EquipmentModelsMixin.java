package dev.yurisuika.dyed.mixin.minecraft.world.item.equipment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.EquipmentModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EquipmentModels.class)
public interface EquipmentModelsMixin {

    @Redirect(method = "bootstrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/equipment/EquipmentModel;builder()Lnet/minecraft/world/item/equipment/EquipmentModel$Builder;", ordinal = 0))
    private static EquipmentModel.Builder redirectLeather() {
        return EquipmentModel.builder()
                .addHumanoidLayers(ResourceLocation.withDefaultNamespace("leather"), true)
                .addHumanoidLayers(ResourceLocation.withDefaultNamespace("leather_overlay"), false)
                .addLayers(EquipmentModel.LayerType.HORSE_BODY, EquipmentModel.Layer.leatherDyeable(ResourceLocation.withDefaultNamespace("leather"), true))
                .addLayers(EquipmentModel.LayerType.HORSE_BODY, EquipmentModel.Layer.leatherDyeable(ResourceLocation.withDefaultNamespace("leather_overlay"), false));
    }

}