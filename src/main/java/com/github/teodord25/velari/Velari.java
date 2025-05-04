package com.github.teodord25.velari;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import com.github.teodord25.velari.block.ModBlocks;
import com.github.teodord25.velari.item.ModItems;
import com.github.teodord25.velari.world.gen.PlanetChunkGenerator;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod("velari")
public class Velari {
    public static final String MOD_ID = "velari";

    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, MOD_ID);

    public static final Supplier<MapCodec<? extends ChunkGenerator>> PLANET_GEN =
            CHUNK_GENS.register("planet_chunk_generator", () -> PlanetChunkGenerator.CODEC);


    public Velari(IEventBus modBus, ModContainer modContainer) {
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);

        NeoForge.EVENT_BUS.register(this);

        CHUNK_GENS.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
            event.accept(ModItems.BISMUTH);

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(ModBlocks.BISMUTH_BLOCK);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) { }
    }

    /* ---------------------------------------------------------
       Native loader – extracts lib from JAR and loads via JNI
    --------------------------------------------------------- */
    static {
        String arch = System.getProperty("os.arch").contains("64") ? "x86_64" : "x86";
        String os = switch (System.getProperty("os.name").toLowerCase()) {
            case String s when s.contains("win")  -> "pc-windows-gnu";
            case String s when s.contains("mac")  -> "apple-darwin";
            default -> "unknown-linux-gnu";
        };
        String libName = os.contains("windows") ? "orbit_engine.dll"
                     : os.contains("darwin")   ? "liborbit_engine.dylib"
                     :                           "liborbit_engine.so";
        String path = "/natives/" + arch + "-" + os + "/" + libName;
        try {
            java.nio.file.Path temp = java.nio.file.Files.createTempFile("orbit_engine", libName);
            try (var in = Velari.class.getResourceAsStream(path)) {
                java.nio.file.Files.copy(in, temp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            System.load(temp.toAbsolutePath().toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load native library " + path, e);
        }
    }
}
