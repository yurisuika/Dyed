package dev.yurisuika.dyed.mixin.minecraft.client.data.models;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.BiConsumer;

@Mixin(ItemModelGenerators.class)
public interface ItemModelGeneratorsAccessor {

    @Accessor
    ItemModelOutput getItemModelOutput();

    @Accessor
    BiConsumer<ResourceLocation, ModelInstance> getModelOutput();

}