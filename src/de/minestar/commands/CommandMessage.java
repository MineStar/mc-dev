package de.minestar.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandUtils;
import de.minestar.commandsystem.annotations.Alias;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "tell")
@Alias(aliases = {"m", "msg", "whisper"})
@Arguments(arguments = "<PlayerName> <Message> [...]")
public class CommandMessage extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(argumentList.getString(0));
        if (target == null) {
            sender.sendError("Player not found");
        } else if (target == sender) {
            sender.sendError("You can't whisper yourself!");
        } else {
            String text = getText(argumentList);
            target.sendInfo("\u00a77\u00a7o" + target.translateString("commands.message.display.incoming", new Object[]{sender.getCommandSenderName(), text}));
            sender.sendInfo("\u00a77\u00a7o" + sender.translateString("commands.message.display.outgoing", new Object[]{target.getCommandSenderName(), text}));
        }
    }

    private String getText(ArgumentList argumentList) {
        StringBuilder sBuilder = new StringBuilder();
        for (String s : argumentList.getIterator(String.class, 1)) {
            sBuilder.append(s);
            sBuilder.append(' ');
        }

        return sBuilder.toString();
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] arguments) {
        if (arguments.length == 1) {
            String lastArgument = arguments[arguments.length - 1];
            ArrayList<String> list = new ArrayList<String>();
            String[] playerList = MinecraftServer.getServer().getPlayerNamesAsList();

            for (int index = 0; index < playerList.length; ++index) {
                String playerName = playerList[index];
                if (CommandUtils.doesStringStartWith(lastArgument, playerName)) {
                    list.add(playerName);
                }
            }

            return list;
        } else {
            return null;
        }
    }
}
