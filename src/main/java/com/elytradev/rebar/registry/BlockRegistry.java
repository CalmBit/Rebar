/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017:
 * 	Ethan Brooks (CalmBit),
 * 	and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
