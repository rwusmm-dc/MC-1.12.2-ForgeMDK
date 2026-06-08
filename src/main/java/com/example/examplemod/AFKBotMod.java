package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    public void init(FMLInitializationEvent event)
    {
        // Event handlers are automatically registered via @Mod.EventBusSubscriber
        logger.info("AFK Bot Mod initialized!");
    }
}
