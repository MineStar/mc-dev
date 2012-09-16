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
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            String var3 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList<String> var4 = new ArrayList<String>();
            String[] var5 = MinecraftServer.getServer().getPlayerNamesAsList();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String var8 = var5[var7];

                if (!MinecraftServer.getServer().getConfigurationManager().isOp(var8) && CommandUtils.doesStringStartWith(var3, var8)) {
                    var4.add(var8);
                }
            }

            return var4;
        } else {
            return null;
        }
    }
}
