package com.github.teodord25.velari;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod("velari")
public class Velari {
    public static final String MODID = "velari";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
        BuiltInRegistries.BLOCK, // Registry to register to
        Velari.MODID // Mod ID
    );

    public static final DeferredHolder<Block, Block> JOE_BLOCK = BLOCKS.register(
        "joe_block", // Registry ENTRY name (must be lowercase)
        registryName -> new Block(
            BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
        )
    );

    public Velari(IEventBus modBus) {
        BLOCKS.register(modBus); // Register blocks to the mod event bus
    }
}

