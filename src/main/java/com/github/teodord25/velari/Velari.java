package com.github.teodord25.velari;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import com.github.teodord25.velari.block.ModBlocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod("velari")
public class Velari {
    public static final String MODID = "velari";

    public Velari(IEventBus modBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModBlocks.BLOCKS.register(modBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    private void addCreative(BuildCreativeModeTabContentsEvent event) { }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) { }
    }
}
