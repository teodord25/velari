package com.github.teodord25.velari;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import com.github.teodord25.velari.block.ModBlocks;

@Mod("velari")
public class Velari {
    public static final String MODID = "velari";

    public Velari(IEventBus modBus) {
        ModBlocks.BLOCKS.register(modBus);
    }
}
