package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "toggledownfall")
@Arguments(arguments = "")
@Description(description = "")
public class CommandToggleDownfall extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().theWorldServer[0].commandToggleDownfall();
        MinecraftServer.getServer().theWorldServer[0].getWorldInfo().setThundering(true);
        MinestarCommandHandler.notifyAdmins(sender, "commands.downfall.success");
    }

}
