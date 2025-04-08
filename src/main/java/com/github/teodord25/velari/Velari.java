package com.github.teodord25.velari;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod("velari")
public class Velari {
    public static final String MODID = "velari";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Velari.MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock(
        "joe_block",
        BlockBehaviour.Properties
            .of()
            .lightLevel((BlockState) -> 15));

    public Velari(IEventBus modBus) {
        BLOCKS.register(modBus); // Register blocks to the mod event bus
    }
}
