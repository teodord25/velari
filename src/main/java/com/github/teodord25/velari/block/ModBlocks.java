package com.github.teodord25.velari.block;

import java.util.function.Supplier;

import com.github.teodord25.velari.Velari;
import com.github.teodord25.velari.item.ModItems;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Velari.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static final DeferredBlock<Block> BISMUTH_BLOCK = registerBlock(
        "bismuth_block",
        () -> new Block(
            BlockBehaviour.Properties.of()
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.AMETHYST)
        ));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
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
