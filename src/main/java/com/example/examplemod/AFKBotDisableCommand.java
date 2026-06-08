package com.example.examplemod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;

public class AFKBotDisableCommand extends CommandBase implements IClientCommand
{
    @Override
    public String getName()
    {
        return "afkbotdisable";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/afkbotdisable or /afkbotd - Stops the AFK bot";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        AFKBotService service = AFKBotService.getInstance();

        if (!service.isActive())
        {
            sender.sendMessage(new TextComponentString(
                TextFormatting.RED + "AFK Bot is not running!"
            ));
        }
        else
        {
            service.stop();
            sender.sendMessage(new TextComponentString(
                TextFormatting.GREEN + "AFK Bot stopped!"
            ));
        }
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message)
    {
        return false;
    }

    @Override
    public java.util.List<String> getAliases()
    {
        java.util.List<String> aliases = new java.util.ArrayList<>();
        aliases.add("afkbotd");
        return aliases;
    }
}
