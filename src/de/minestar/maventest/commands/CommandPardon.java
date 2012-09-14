package de.minestar.maventest.commands;

import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "pardon")
@Arguments(arguments = "<PlayerName>")
public class CommandPardon extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().removeBan(argumentList.getString(0));
        MinestarCommandHandler.notifyAdmins(sender, "commands.unban.success", argumentList.getString(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().func_73712_c().keySet()) : null;
    }
}
