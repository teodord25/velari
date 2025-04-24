package com.github.teodord25.velari.block;

import java.util.function.Function;
import java.util.function.Supplier;

import com.github.teodord25.velari.Velari;
import com.github.teodord25.velari.item.ModItems;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Velari.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static final DeferredBlock<Block> BISMUTH_BLOCK = registerBlockAndItsItem(
        "bismuth_block",
        props -> new Block(
            props
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.AMETHYST)
        ));


    private static <B extends Block> DeferredBlock<B> registerBlockAndItsItem(String name, Function<BlockBehaviour.Properties, B> block) {
        DeferredBlock<B> deferredBlock = BLOCKS.registerBlock(name, block);
        ModItems.ITEMS.registerSimpleBlockItem(name, deferredBlock);
        return deferredBlock;
    }

    // NOTE: used for simple blocks that use Block::new
    public static final DeferredBlock<Block> joe_block = BLOCKS.registerSimpleBlock("joe_block",
        BlockBehaviour.Properties.of().lightLevel((BlockState) -> 1));

    // NOTE: if you want to pass a subclass of block use this
    public static final DeferredBlock<Block> tom_trtan_block = BLOCKS.registerBlock(
        "tom_trtan",
        SlabBlock::new,
        BlockBehaviour.Properties
            .of()
            .lightLevel((BlockState) -> 15));
}
