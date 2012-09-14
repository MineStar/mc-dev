package de.minestar.maventest.commands;

import java.util.regex.Pattern;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BanEntry;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "ban")
@Arguments(arguments = "<PlayerName> [Reason...]")
@Description(description = "")
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
}
