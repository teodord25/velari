package com.github.teodord25.velari.events.server;

import com.github.teodord25.velari.Velari;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Velari.MODID)
public class LogicalServerEventHandler {
    // NOTE: handlers can one of 5 priority settings, they will be executed from highest to lowest
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void test() { }
}
