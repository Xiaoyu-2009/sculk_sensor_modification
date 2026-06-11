package dev.xiaoyu.sculk_sensor_modification;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.architectury.platform.Platform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(SculkSensorModification.MOD_ID);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<String>>() {}.getType();

    private static final List<String> DEFAULT_EXCLUDED = Arrays.asList(
            "minecraft:warden",
            "minecraft:cod",
            "minecraft:salmon",
            "minecraft:tropical_fish",
            "minecraft:pufferfish",
            "minecraft:armor_stand",
            "minecraft:bee",
            "minecraft:bat",
            "minecraft:wither",
            "minecraft:squid",
            "minecraft:glow_squid"
    );

    private static List<String> excludedEntities;

    public static void init() {
        Path configPath = Platform.getConfigFolder().resolve(SculkSensorModification.MOD_ID + ".json");
        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                excludedEntities = GSON.fromJson(reader, LIST_TYPE);
            } catch (IOException e) {
                LOGGER.error("Failed to load config", e);
            }
        }
        if (excludedEntities == null) {
            excludedEntities = new ArrayList<>(DEFAULT_EXCLUDED);
            try {
                Files.createDirectories(configPath.getParent());
                try (Writer writer = Files.newBufferedWriter(configPath)) {
                    GSON.toJson(excludedEntities, LIST_TYPE, writer);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to save config", e);
            }
        }
    }

    public static boolean isEntityExcluded(Entity entity) {
        return excludedEntities.contains(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
    }
}