package de.minestar.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.CommandUtils;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "op")
@Arguments(arguments = "<PlayerName>")
public class CommandOp extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().getConfigurationManager().addOp(argumentList.getString(0));
        CommandHandler.notifyAdmins(sender, "command.op.success", argumentList.getString(0));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] arguments) {
        if (arguments.length == 1) {
            String lastArgument = arguments[arguments.length - 1];
            ArrayList<String> list = new ArrayList<String>();
            String[] playerList = MinecraftServer.getServer().getPlayerNamesAsList();

            for (int index = 0; index < playerList.length; ++index) {
                String playerName = playerList[index];
                if (!MinecraftServer.getServer().getConfigurationManager().isOp(playerName) && CommandUtils.doesStringStartWith(lastArgument, playerName)) {
                    list.add(playerName);
                }
            }

            return list;
        } else {
            return null;
        }
    }
}
