package com.ltwextra.mixin;

import com.ltwextra.cache.RenderDataCache;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureManager.class)
public class ResourceCacheMixin {

    // Feature 3: Smart Cache - Bypasses continuous texture path re-lookups across cycles
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void onGetTexture(Identifier id, CallbackInfoReturnable<AbstractTexture> cir) {
        String cacheKey = "tex_" + id.toString();
        
        // Return memory cached allocation reference immediately if already built
        AbstractTexture cached = RenderDataCache.getOrCreate(cacheKey, () -> null);
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }
}
