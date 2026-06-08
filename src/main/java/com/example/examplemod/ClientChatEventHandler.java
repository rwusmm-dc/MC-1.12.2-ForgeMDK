package com.example.examplemod;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventbus.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public class ClientChatEventHandler
{
    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event)
    {
        String message = event.getMessage();

        // Handle /afkbot command
        if (message.startsWith("/afkbot ") || message.equals("/afkbot"))
        {
            event.setCanceled(true);
            
            AFKBotService service = AFKBotService.getInstance();
            if (service.isActive())
            {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
                    TextFormatting.RED + "AFK Bot is already running!"
                ));
            }
            else
            {
                service.start();
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
                    TextFormatting.GREEN + "AFK Bot started! You can AFK now."
                ));
            }
            return;
        }

        // Handle /afkbotdisable or /afkbotd commands
        if (message.startsWith("/afkbotdisable") || message.startsWith("/afkbotd") || 
            message.equals("/afkbotdisable") || message.equals("/afkbotd"))
        {
            event.setCanceled(true);
            
            AFKBotService service = AFKBotService.getInstance();
            if (!service.isActive())
            {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
                    TextFormatting.RED + "AFK Bot is not running!"
                ));
            }
            else
            {
                service.stop();
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
                    TextFormatting.GREEN + "AFK Bot stopped!"
                ));
            }
            return;
        }

        // Handle /brightday command
        if (message.equals("/brightday"))
        {
            event.setCanceled(true);
            
            BrightDayHandler.toggle();
            if (BrightDayHandler.isBrightDayEnabled())
            {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
                    TextFormatting.YELLOW + "Bright Day enabled! Everything is now fully lit."
                ));
            }
            else
            {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
                    TextFormatting.GRAY + "Bright Day disabled! Normal brightness restored."
                ));
            }
            return;
        }
    }
}
