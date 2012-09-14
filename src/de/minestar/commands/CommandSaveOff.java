package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "save-off")
@Arguments(arguments = "")
public class CommandSaveOff extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        MinecraftServer server = MinecraftServer.getServer();

        for (int i = 0; i < server.theWorldServer.length; ++i) {
            if (server.theWorldServer[i] != null) {
                server.theWorldServer[i].levelSaving = true;
            }
        }

        CommandHandler.notifyAdmins(sender, "commands.save.disabled");
    }

}
