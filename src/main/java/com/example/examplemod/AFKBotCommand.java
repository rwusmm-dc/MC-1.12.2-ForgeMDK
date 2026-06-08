package com.example.examplemod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

public class AFKBotCommand extends CommandBase implements IClientCommand
{
    @Override
    public String getName()
    {
        return "afkbot";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/afkbot - Starts the AFK bot";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        AFKBotService service = AFKBotService.getInstance();

        if (service.isActive())
        {
            sender.sendMessage(new TextComponentString(
                TextFormatting.RED + "AFK Bot is already running!"
            ));
        }
        else
        {
            service.start();
            sender.sendMessage(new TextComponentString(
                TextFormatting.GREEN + "AFK Bot started! You can AFK now."
            ));
        }
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message)
    {
        return false;
    }
}
