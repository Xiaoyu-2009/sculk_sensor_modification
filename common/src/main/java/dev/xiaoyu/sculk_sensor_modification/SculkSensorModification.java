package dev.xiaoyu.sculk_sensor_modification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SculkSensorModification {
    public static final String MOD_ID = "sculk-sensor-modification";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        Config.init();
    }
}