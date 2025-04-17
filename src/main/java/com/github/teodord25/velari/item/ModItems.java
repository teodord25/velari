package com.github.teodord25.velari.item;

import com.github.teodord25.velari.Velari;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Velari.MODID);

        // public static final DeferredItem<Item> BISMUTH = ITEMS.registerItem("bismuth", Item::new, new Item.Properties());
        public static final DeferredItem<Item> BISMUTH = ITEMS.registerSimpleItem("bismuth", new Item.Properties());

        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }
}
