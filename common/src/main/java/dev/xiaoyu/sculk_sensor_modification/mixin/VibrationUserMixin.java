package dev.xiaoyu.sculk_sensor_modification.mixin;

import dev.xiaoyu.sculk_sensor_modification.Config;
import dev.xiaoyu.sculk_sensor_modification.mixin.accessor.SculkShriekerBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("JavadocDeclaration")
@Mixin(targets = "net.minecraft.world.level.block.entity.SculkShriekerBlockEntity$VibrationUser")
public class VibrationUserMixin {

    @Final
    @Shadow
    private PositionSource positionSource;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, GameEvent.Context context) {
        return !(Boolean) serverLevel.getBlockState(
                BlockPos.containing(positionSource.getPosition(serverLevel).orElseThrow())
        ).getValue(SculkShriekerBlock.SHRIEKING)
                && context.sourceEntity() != null
                && !Config.isEntityExcluded(context.sourceEntity());
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
        Entity sourceEntity = entity2 != null ? entity2 : entity;
        if (sourceEntity == null || Config.isEntityExcluded(sourceEntity)) return;

        Vec3 posVec = positionSource.getPosition(serverLevel).orElseThrow();
        if (!(serverLevel.getBlockEntity(BlockPos.containing(posVec)) instanceof SculkShriekerBlockEntity shrieker)) return;
        if (shrieker.getBlockState().getValue(SculkShriekerBlock.SHRIEKING)) return;

        if (!serverLevel.getEntitiesOfClass(Warden.class, AABB.ofSize(posVec, 48, 48, 48)).isEmpty()) return;

        ServerPlayer serverPlayer = SculkShriekerBlockEntity.tryGetPlayer(sourceEntity);
        if (serverPlayer != null) {
            shrieker.tryShriek(serverLevel, serverPlayer);
        } else {
            SculkShriekerBlockEntityAccessor accessor = (SculkShriekerBlockEntityAccessor) shrieker;
            accessor.setWarningLevel(Math.min(accessor.getWarningLevel() + 1, 4));
            accessor.invokeShriek(serverLevel, sourceEntity);
        }
    }
}