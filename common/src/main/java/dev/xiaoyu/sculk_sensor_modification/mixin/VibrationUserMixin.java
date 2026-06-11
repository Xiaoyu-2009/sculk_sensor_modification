package dev.xiaoyu.sculk_sensor_modification.mixin;

import dev.xiaoyu.sculk_sensor_modification.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.util.SpawnUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.level.block.entity.SculkSensorBlockEntity$VibrationUser")
public class VibrationUserMixin {

    @Final
    @Shadow
    protected BlockPos blockPos;

    @Unique
    private int sculk_sensor_modification$warningLevel;

    @Inject(method = "onReceiveVibration", at = @At("TAIL"))
    private void onOnReceiveVibration(ServerLevel arg, BlockPos arg2, GameEvent arg3, Entity arg4, Entity arg5, float f, CallbackInfo ci) {
        Entity sourceEntity = arg5 != null ? arg5 : arg4;
        if (sourceEntity == null || Config.isEntityExcluded(sourceEntity)) return;

        if (arg.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING) && arg.getDifficulty() != Difficulty.PEACEFUL) {
            this.sculk_sensor_modification$warningLevel++;
            if (this.sculk_sensor_modification$warningLevel >= 4) {
                this.sculk_sensor_modification$warningLevel = 0;
                SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, arg, this.blockPos, 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER);
            }
        }
    }
}