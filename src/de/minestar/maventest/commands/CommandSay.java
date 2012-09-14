package de.minestar.maventest.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.Packet3Chat;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "say")
@Arguments(arguments = "<Message> [...]")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters.")
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

    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length >= 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getPlayerNamesAsList()) : null;
    }

    /**
     * Returns a List of strings (chosen from the given strings) which the last word in the given string array is a beginning-match for. (Tab completion).
     */
    public static List getListOfStringsMatchingLastWord(String[] par0ArrayOfStr, String... par1ArrayOfStr) {
        String var2 = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList var3 = new ArrayList();
        String[] var4 = par1ArrayOfStr;
        int var5 = par1ArrayOfStr.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];

            if (doesStringStartWith(var2, var7)) {
                var3.add(var7);
            }
        }

        return var3;
    }
}
