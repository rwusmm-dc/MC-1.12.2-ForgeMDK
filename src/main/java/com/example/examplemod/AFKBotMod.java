package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = AFKBotMod.MODID, name = AFKBotMod.NAME, version = AFKBotMod.VERSION, clientSideOnly = true)
public class AFKBotMod
{
    public static final String MODID = "afkbot";
    public static final String NAME = "AFK Bot Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;
    public static AFKBotMod instance;

    public AFKBotMod()
    {
        instance = this;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void init(FMLInitializationEvent event)
    {
        // Register event handlers for client-side functionality
        MinecraftForge.EVENT_BUS.register(ClientChatEventHandler.class);
        MinecraftForge.EVENT_BUS.register(AFKBotTickHandler.class);
        MinecraftForge.EVENT_BUS.register(BrightDayHandler.class);
        logger.info("AFK Bot Mod initialized!");
    }
}
