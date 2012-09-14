package de.minestar.maventest.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "kick")
@Arguments(arguments = "<PlayerName> [Reason...]")
public class CommandKick extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        String playerName = argumentList.getString(0);
        String reason = "";

        for (String txt : argumentList.getIterator(String.class, 1)) {
            reason += txt + " ";
        }
        if (reason.length() > 1) {
            reason = reason.substring(0, reason.length() - 1);
        } else {
            reason = "Kicked by an operator.";
        }

        EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);

        if (player == null) {
            sender.sendError("Player not found!");
            return;
        }

        player.playerNetServerHandler.kickPlayer(reason);

        if (reason.length() > 0) {
            MinestarCommandHandler.notifyAdmins(sender, "commands.kick.success.reason", new Object[]{player.getEntityName(), reason});
        } else {
            MinestarCommandHandler.notifyAdmins(sender, "commands.kick.success", new Object[]{player.getEntityName()});
        }
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

                if (doesStringStartWith(var3, var8)) {
                    var4.add(var8);
                }
            }

            return var4;
        } else {
            return null;
        }
    }
}
