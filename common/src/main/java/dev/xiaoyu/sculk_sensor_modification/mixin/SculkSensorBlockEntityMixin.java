package dev.xiaoyu.sculk_sensor_modification.mixin;

import dev.xiaoyu.sculk_sensor_modification.VibrationUserWrapper;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSensorBlockEntity.class)
public class SculkSensorBlockEntityMixin {

    @Inject(method = "createVibrationUser", at = @At("RETURN"), cancellable = true)
    private void onReturnVibrationUser(CallbackInfoReturnable<VibrationSystem.User> cir) {
        cir.setReturnValue(new VibrationUserWrapper(cir.getReturnValue()));
    }
}