package com.github.teodord25.velari.block;

import com.github.teodord25.velari.Velari;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
        public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Velari.MODID);

        // NOTE: used for simple blocks that use Block::new
        public static final DeferredBlock<Block> joe_block = BLOCKS.registerSimpleBlock(
                        "joe_block",
                        BlockBehaviour.Properties
                                        .of()
                                        .lightLevel((BlockState) -> 1));

        // NOTE: if you want to pass a subclass of block use this
        public static final DeferredBlock<Block> tom_trtan_block = BLOCKS.registerBlock(
                        "tom_trtan",
                        SlabBlock::new,
                        BlockBehaviour.Properties
                                        .of()
                                        .lightLevel((BlockState) -> 15));
}
