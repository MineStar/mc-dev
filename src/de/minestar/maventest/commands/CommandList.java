package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "list")
@Arguments(arguments = "")
public class CommandList extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        sender.sendInfo(sender.translateString("commands.players.list", new Object[]{Integer.valueOf(MinecraftServer.getServer().playersOnline()), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers())}));
        sender.sendInfo(MinecraftServer.getServer().getConfigurationManager().getPlayerList());
    }

}
