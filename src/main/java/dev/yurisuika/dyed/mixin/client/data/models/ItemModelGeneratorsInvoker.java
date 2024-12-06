package dev.yurisuika.dyed.mixin.client.data.models;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemModelGenerators.class)
public interface ItemModelGeneratorsInvoker {

    @Invoker("createFlatItemModel")
    ResourceLocation invokeCreateFlatItemModel(Item item, ModelTemplate modelTemplate);

    @Invoker("generateWolfArmor")
    void invokeGenerateWolfArmor(Item item);

}