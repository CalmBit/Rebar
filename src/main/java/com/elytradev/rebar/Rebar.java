/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2017:
 *      Ethan Brooks (CalmBit),
 *      and contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of
 *  this software and associated documentation files (the "Software"), to deal in
 *  the Software without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do
 *  so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.elytradev.rebar;

import com.elytradev.concrete.network.NetworkContext;
import com.elytradev.concrete.utilpackets.MessageToast;
import com.elytradev.rebar.generic.RebarCreativeTab;
import com.elytradev.rebar.proxy.CommonProxy;
import com.elytradev.rebar.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Rebar.MOD_ID,
        name = Rebar.NAME,
        version = Rebar.VERSION)
@Mod.EventBusSubscriber
public final class Rebar {

    public static final String MOD_ID = "rebar";
    public static final String NAME = "Rebar";
    public static final String VERSION = "1.12.1-0.1.0";

    public static final Logger LOG = LogManager.getLogger(Rebar.NAME);

    @SidedProxy(
            clientSide = "com.elytradev.rebar.proxy.ClientProxy",
            serverSide = "com.elytradev.rebar.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static RebarCreativeTab CREATIVE_TAB = new RebarCreativeTab(Rebar.NAME);

    @Mod.Instance
    public static Rebar INSTANCE;

    public static NetworkContext NETWORK_CONTEXT;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BlockRegistry.registerBlocks(event.getRegistry());

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        BlockRegistry.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BlockRegistry.registerBlockModels();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PROXY.init();
        NETWORK_CONTEXT = NetworkContext.forChannel("rebar");
        NETWORK_CONTEXT.register(MessageToast.class);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }



}
