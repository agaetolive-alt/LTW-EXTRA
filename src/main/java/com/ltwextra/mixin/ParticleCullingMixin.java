package com.ltwextra.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public class ParticleCullingMixin {

    @Inject(method = "buildGeometry", at = @At("HEAD"), cancellable = true)
    private void onBuildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Particle particle = (Particle) (Object) this;
        
        // Dynamic Distance Culling: Don't build geometries for far away particles
        double maxDistanceSq = 576; // 24 blocks squared
        double dx = particle.getBoundingBox().getCenter().x - camera.getPos().x;
        double dy = particle.getBoundingBox().getCenter().y - camera.getPos().y;
        double dz = particle.getBoundingBox().getCenter().z - camera.getPos().z;
        
        if ((dx * dx + dy * dy + dz * dz) > maxDistanceSq) {
            ci.cancel();
        }
    }
}
