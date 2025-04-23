package com.github.teodord25.velari.events.client;

import com.github.teodord25.velari.Velari;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Velari.MODID)
public class LogicalClientEventHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void test() { }
}
