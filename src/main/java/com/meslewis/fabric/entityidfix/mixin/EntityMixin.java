package com.meslewis.fabric.entityidfix.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Entity.class)
public abstract class EntityMixin {
    private static final AtomicInteger CLIENT_CURRENT_ID = new AtomicInteger();

    //Intercept incrementAndGet in the Entity constructor to give the client its own AtomicInteger.
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/atomic/AtomicInteger;incrementAndGet()I"))
    private int getCurrentId(AtomicInteger instance, EntityType<?> type, World world) {
        if(world.isClient()) {
            return CLIENT_CURRENT_ID.incrementAndGet();
        } else {
            return instance.incrementAndGet();
        }
    }
}
