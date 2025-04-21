package com.github.teodord25.velari.client;

import com.github.teodord25.velari.Velari;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/// TODO: client only code goes here?
@Mod(value = Velari.MODID, dist = Dist.CLIENT) 
public class VelariClient {
    public VelariClient(IEventBus modBus) {
        // Perform logic in that should only be executed on the physical client
        System.out.println(Minecraft.getInstance());
    }
}
