package de.minestar.commands.whitelist;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "on")
@Arguments(arguments = "")
public class CommandWhitelistOn extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(true);
        CommandHandler.notifyAdmins(sender, "commands.whitelist.enabled");
    }

}
