package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "stop")
@Arguments(arguments = "")
public class CommandStop extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        CommandHandler.notifyAdmins(sender, "commands.stop.start");
        MinecraftServer.getServer().initiateShutdown();
    }

}
