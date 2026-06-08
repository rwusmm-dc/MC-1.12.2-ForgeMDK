package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventbus.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class BrightDayHandler
{
    private static boolean isBrightDay = false;
    private static float originalGamma = 1.0f;

    public static void toggle()
    {
        isBrightDay = !isBrightDay;
        if (!isBrightDay)
        {
            // Restore original gamma
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.gameSettings != null)
            {
                mc.gameSettings.gammaSetting = originalGamma;
            }
        }
    }

    public static boolean isBrightDayEnabled()
    {
        return isBrightDay;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.START)
        {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.gameSettings != null)
        {
            if (isBrightDay && mc.world != null)
            {
                // Set maximum brightness/gamma
                mc.gameSettings.gammaSetting = 16.0f; // Max gamma (higher values = brighter)
            }
        }
    }
}
