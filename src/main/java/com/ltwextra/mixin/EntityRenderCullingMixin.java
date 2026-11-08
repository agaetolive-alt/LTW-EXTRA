package com.ltwextra.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderCullingMixin {

    // Feature 1: Smart Filter - Entity Culling via Camera Frustum & Dynamic Distance Culling
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void onRenderEntity(E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        // Skip rendering your own vehicle if in first-person (Sodium compatibility friendly)
        if (entity == client.player.getVehicle() && client.options.getPerspective().isFirstPerson()) {
            return;
        }

        // Distance Check (Save GPU/CPU: Cull non-visible objects past 24 blocks)
        double distanceSq = client.player.squaredDistanceTo(entity);
        if (distanceSq > 576) { // 24 * 24 = 576 blocks squared
            // If the entity is far away and doesn't trigger standard targeted raycasting, we skip it
            if (client.targetedEntity != entity) {
                ci.cancel();
                return;
            }
        }

        // Smart Merge/Filter: Crosscheck against Sodium/Vanilla frustum data
        Frustum frustum = client.worldRenderer.getFrustum();
        if (frustum != null && !frustum.isVisible(entity.getVisibilityBoundingBox())) {
            ci.cancel();
        }
    }
}
