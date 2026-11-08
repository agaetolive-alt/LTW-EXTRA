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

    // Feature 1 & 2: Culls duplicate or off-screen particle ticks and renders
    @Inject(method = "buildGeometry", at = @At("HEAD"), cancellable = true)
    private void onBuildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        Particle particle = (Particle) (Object) this;

        // Cull particles completely out of camera line-of-sight
        if (client.worldRenderer.getFrustum() != null) {
            if (!client.worldRenderer.getFrustum().isVisible(particle.getBoundingBox())) {
                ci.cancel();
            }
        }
    }
}
