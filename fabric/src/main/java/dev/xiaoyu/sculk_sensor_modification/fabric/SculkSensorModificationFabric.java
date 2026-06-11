package dev.xiaoyu.sculk_sensor_modification.fabric;

import dev.xiaoyu.sculk_sensor_modification.SculkSensorModification;
import net.fabricmc.api.ModInitializer;

public final class SculkSensorModificationFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SculkSensorModification.init();
    }
}