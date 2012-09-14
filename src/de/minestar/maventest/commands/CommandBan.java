package de.minestar.maventest.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BanEntry;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "ban")
@Arguments(arguments = "<PlayerName> [Reason...]")
public class CommandBan extends AbstractCommand {

    public static final Pattern IPMatcher = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

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
            reason = "Banned by an operator.";
        }

        // search the player
        EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);
        if (player == null) {
            sender.sendMessage("§cPlayer '" + playerName + "' not found!");
            return;
        }

        // add the player to the banlist
        BanEntry banEntry = new BanEntry(playerName);
        banEntry.setSource(sender.getCommandSenderName());
        banEntry.setReason(reason);
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().addBan(banEntry);

        // kick him
        player.playerNetServerHandler.kickPlayer("You are banned from this server.");

        // send notification
        MinestarCommandHandler.notifyAdmins(sender, "commands.ban.success", new Object[]{playerName});
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
