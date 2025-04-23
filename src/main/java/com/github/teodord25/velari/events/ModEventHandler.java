package com.github.teodord25.velari.events;

import com.github.teodord25.velari.Velari;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Velari.MODID, bus = Bus.MOD)
public class ModEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void test() {

    }
}

