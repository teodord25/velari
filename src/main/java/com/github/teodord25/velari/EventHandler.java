package com.github.teodord25.velari;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber(modid = Velari.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void test(LivingEvent.LivingJumpEvent event) {
        var entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            player.sendSystemMessage(Component.literal("You're a player!"));
        }
    }
}
