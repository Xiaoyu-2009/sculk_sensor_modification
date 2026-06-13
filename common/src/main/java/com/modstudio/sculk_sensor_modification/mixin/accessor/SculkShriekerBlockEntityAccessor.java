package com.modstudio.sculk_sensor_modification.mixin.accessor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SculkShriekerBlockEntity.class)
public interface SculkShriekerBlockEntityAccessor {

    @Accessor("warningLevel")
    int getWarningLevel();

    @Accessor("warningLevel")
    void setWarningLevel(int warningLevel);

    @Invoker("shriek")
    void invokeShriek(ServerLevel serverLevel, @Nullable Entity entity);
}