package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.MinestarCommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "toggledownfall")
@Arguments(arguments = "")
public class CommandToggleDownfall extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().theWorldServer[0].commandToggleDownfall();
        MinecraftServer.getServer().theWorldServer[0].getWorldInfo().setThundering(true);
        MinestarCommandHandler.notifyAdmins(sender, "commands.downfall.success");
    }

}
