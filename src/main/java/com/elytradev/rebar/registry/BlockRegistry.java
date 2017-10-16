package com.elytradev.rebar.registry;

import com.elytradev.rebar.Rebar;
import com.elytradev.rebar.block.BlockBase;
import com.elytradev.rebar.block.BlockToaster;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry {

    public static BlockBase toaster = new BlockToaster().setCreativeTab(Rebar.CREATIVE_TAB);

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.registerAll(
                toaster
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                toaster.getItemBlock()
        );
    }

    public static void registerBlockModels() {
        registerBlockModel(toaster);
    }

    private static void registerBlockModel(BlockBase block) {
        block.registerItemModel((ItemBlock)Item.getItemFromBlock(block));
    }
}
