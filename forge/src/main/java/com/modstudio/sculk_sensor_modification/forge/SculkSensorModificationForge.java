package com.modstudio.sculk_sensor_modification.forge;

import dev.architectury.platform.forge.EventBuses;
import com.modstudio.sculk_sensor_modification.SculkSensorModification;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SculkSensorModification.MOD_ID)
public final class SculkSensorModificationForge {

    @SuppressWarnings("removal")
    public SculkSensorModificationForge() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(SculkSensorModification.MOD_ID, modEventBus);

        SculkSensorModification.init();
    }
}