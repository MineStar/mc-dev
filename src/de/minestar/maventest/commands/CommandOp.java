package de.minestar.maventest.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "op")
@Arguments(arguments = "<PlayerName>")
@Description(description = "")
public class CommandOp extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        MinecraftServer.getServer().getConfigurationManager().addOp(argumentList.getString(0));
        MinestarCommandHandler.notifyAdmins(sender, "command.op.success", argumentList.getString(0));
    }

    // TODO: Implement tab completion

    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            String var3 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList var4 = new ArrayList();
            String[] var5 = MinecraftServer.getServer().getPlayerNamesAsList();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String var8 = var5[var7];

                if (!MinecraftServer.getServer().getConfigurationManager().isOp(var8) && doesStringStartWith(var3, var8)) {
                    var4.add(var8);
                }
            }

            return var4;
        } else {
            return null;
        }
    }
}
