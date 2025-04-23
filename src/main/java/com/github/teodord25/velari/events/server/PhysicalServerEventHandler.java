package com.github.teodord25.velari.events.server;

import com.github.teodord25.velari.Velari;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Velari.MODID, value = Dist.DEDICATED_SERVER)
public class PhysicalServerEventHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void test() {
        // no idea
    }
}
