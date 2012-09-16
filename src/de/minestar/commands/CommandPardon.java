package de.minestar.commands;

import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.CommandUtils;
import de.minestar.commandsystem.annotations.Alias;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "pardon")
@Alias(aliases = {"unban"})
@Arguments(arguments = "<PlayerName>")
public class CommandPardon extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().removeBan(argumentList.getString(0));
        CommandHandler.notifyAdmins(sender, "commands.unban.success", argumentList.getString(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? CommandUtils.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().func_73712_c().keySet()) : null;
    }
}
