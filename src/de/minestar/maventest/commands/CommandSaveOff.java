package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "save-off")
@Arguments(arguments = "")
@Description(description = "")
public class CommandSaveOff extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        MinecraftServer server = MinecraftServer.getServer();

        for (int i = 0; i < server.theWorldServer.length; ++i) {
            if (server.theWorldServer[i] != null) {
                server.theWorldServer[i].levelSaving = true;
            }
        }

        MinestarCommandHandler.notifyAdmins(sender, "commands.save.disabled");
    }

}
