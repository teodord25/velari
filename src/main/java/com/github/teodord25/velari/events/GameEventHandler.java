package com.github.teodord25.velari.events;

import com.github.teodord25.velari.Velari;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber(modid = Velari.MODID, bus = Bus.GAME)
public class GameEventHandler {
    // NOTE: handlers can one of 5 priority settings, they will be executed
    // from highest to lowest
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void test(LivingEvent.LivingJumpEvent event) {
        var entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            player.sendSystemMessage(Component.literal("You're a player!"));
        }
    }
}
