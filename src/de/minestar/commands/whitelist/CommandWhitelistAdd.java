package de.minestar.commands.whitelist;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "add")
@Arguments(arguments = "<Player>")
public class CommandWhitelistAdd extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().getConfigurationManager().addToWhiteList(argumentList.getString(0));
        CommandHandler.notifyAdmins(sender, "commands.whitelist.add.success", argumentList.getString(0));
    }

}
