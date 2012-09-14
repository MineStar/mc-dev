package de.minestar.maventest.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "pardon")
@Arguments(arguments = "<PlayerName>")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters.")
public class CommandPardon extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().func_73709_b(argumentList.getString(0));
        MinestarCommandHandler.notifyAdmins(sender, "commands.unban.success", argumentList.getString(0));
    }

    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().func_73712_c().keySet()) : null;
    }

    /**
     * Returns a List of strings (chosen from the given string iterable) which
     * the last word in the given string array is a beginning-match for. (Tab
     * completion).
     */
    private List getListOfStringsFromIterableMatchingLastWord(String[] par0ArrayOfStr, Iterable par1Iterable) {
        String var2 = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList var3 = new ArrayList();
        Iterator var4 = par1Iterable.iterator();

        while (var4.hasNext()) {
            String var5 = (String) var4.next();

            if (doesStringStartWith(var2, var5)) {
                var3.add(var5);
            }
        }

        return var3;
    }
}
