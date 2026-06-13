package com.modstudio.sculk_sensor_modification.fabric;

import com.modstudio.sculk_sensor_modification.SculkSensorModification;
import net.fabricmc.api.ModInitializer;

public final class SculkSensorModificationFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SculkSensorModification.init();
    }
}