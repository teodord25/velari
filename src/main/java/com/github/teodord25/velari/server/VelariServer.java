package com.github.teodord25.velari.server;

import com.github.teodord25.velari.Velari;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/// TODO: client only code goes here?

@Mod(value = Velari.MOD_ID, dist = Dist.DEDICATED_SERVER) 
public class VelariServer {
    public VelariServer(IEventBus modBus) {
        // Perform logic in that should only be executed on the physical server
    }
}
