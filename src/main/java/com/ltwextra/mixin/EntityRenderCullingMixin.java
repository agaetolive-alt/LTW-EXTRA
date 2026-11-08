package com.ltwextra.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRenderCullingMixin<T extends Entity> {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void onShouldRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Skip culling if the player is looking directly at the entity
        if (client.targetedEntity == entity) return;

        // Smart Filter: Forcefully stop calculations for entities farther than 24 blocks away (576 blocks squared)
        double distanceSq = client.player.squaredDistanceTo(entity);
        if (distanceSq > 576) {
            cir.setReturnValue(false);
        }
    }
}
