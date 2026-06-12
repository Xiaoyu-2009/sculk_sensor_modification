package dev.xiaoyu.sculk_sensor_modification.mixin;

import dev.xiaoyu.sculk_sensor_modification.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.level.block.entity.SculkShriekerBlockEntity$VibrationUser")
public class SculkShriekerBlockEntityMixin {

    @Inject(method = "onReceiveVibration", at = @At("HEAD"), cancellable = true)
    private void onReceiveVibration(ServerLevel arg, BlockPos arg2, GameEvent arg3, @Nullable Entity arg4, @Nullable Entity arg5, float f, CallbackInfo ci) {
        Entity sourceEntity = arg5 != null ? arg5 : arg4;
        if (sourceEntity == null || Config.isEntityExcluded(sourceEntity)) {
            ci.cancel();
        }
    }
}