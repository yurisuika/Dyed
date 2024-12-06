package dev.yurisuika.dyed.mixin.client.data.models;

import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemModelGenerators.class)
public abstract class ItemModelGeneratorsMixin {

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/data/models/ItemModelGenerators;generateDyedItem(Lnet/minecraft/world/item/Item;I)V"))
    private void redirectLeatherHorseArmor(ItemModelGenerators itemModelGenerators, Item item, int i) {
        generateHorseArmor(item, i);
    }

    @Unique
    private void generateHorseArmor(Item item, int i) {
        ResourceLocation resourceLocationModel = TextureMapping.getItemTexture(item);
        ResourceLocation resourceLocationModelOverlay = TextureMapping.getItemTexture(item, "_overlay");
        ResourceLocation resourceLocationItem = ModelLocationUtils.getModelLocation(item, "_dyed");
        ResourceLocation resourceLocationItemOverlay = ModelTemplates.FLAT_ITEM.create(item, TextureMapping.layer0(resourceLocationModel), ((ItemModelGeneratorsAccessor) this).getModelOutput());
        ModelTemplates.TWO_LAYERED_ITEM.create(resourceLocationItem, TextureMapping.layered(resourceLocationModel, resourceLocationModelOverlay), ((ItemModelGeneratorsAccessor) this).getModelOutput());
        ((ItemModelGeneratorsAccessor) this).getItemModelOutput().accept(item, ItemModelUtils.composite(ItemModelUtils.plainModel(resourceLocationItem), ItemModelUtils.tintedModel(resourceLocationItemOverlay, ItemModelUtils.constantTint(-1), new Dye(i))));
    }

}