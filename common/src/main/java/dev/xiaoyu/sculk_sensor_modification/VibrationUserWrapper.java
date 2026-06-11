package dev.xiaoyu.sculk_sensor_modification;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.util.SpawnUtil;
import org.jetbrains.annotations.NotNull;

public class VibrationUserWrapper implements VibrationSystem.User {
    private final VibrationSystem.User delegate;
    private int warningLevel;

    public VibrationUserWrapper(VibrationSystem.User delegate) {
        this.delegate = delegate;
    }

    @Override
    public int getListenerRadius() {
        return this.delegate.getListenerRadius();
    }

    @Override
    public @NotNull PositionSource getPositionSource() {
        return this.delegate.getPositionSource();
    }

    @Override
    public boolean canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, GameEvent.Context context) {
        return this.delegate.canReceiveVibration(serverLevel, blockPos, gameEvent, context);
    }

    @Override
	public boolean canTriggerAvoidVibration() {
		return this.delegate.canTriggerAvoidVibration();
	}

	@Override
	public boolean requiresAdjacentChunksToBeTicking() {
		return this.delegate.requiresAdjacentChunksToBeTicking();
	}

    @Override
    public void onDataChanged() {
        this.delegate.onDataChanged();
    }

	@Override
	public void onReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, Entity entity, Entity entity2, float f) {
        this.delegate.onReceiveVibration(serverLevel, blockPos, gameEvent, entity, entity2, f);

        Entity sourceEntity = entity2 != null ? entity2 : entity;
        if (sourceEntity == null || Config.isEntityExcluded(sourceEntity)) return;

        if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING) && serverLevel.getDifficulty() != Difficulty.PEACEFUL) {
            this.warningLevel++;
            if (this.warningLevel >= 4) {
                this.warningLevel = 0;
                SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, serverLevel, blockPos, 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER);
            }
        }
    }
}