package de.minestar.commands;

import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.Packet3Chat;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "say")
@Arguments(arguments = "<Message> [...]")
public class CommandSay extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        String text = getText(argumentList);
        MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(String.format("[%s] %s", new Object[]{sender.getCommandSenderName(), text})));
    }

    private String getText(ArgumentList argumentList) {
        StringBuilder sBuilder = new StringBuilder();
        for (String s : argumentList.getIterator(String.class, 0)) {
            sBuilder.append(s);
            sBuilder.append(' ');
        }

        return sBuilder.toString();
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length >= 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getPlayerNamesAsList()) : null;
    }
}
