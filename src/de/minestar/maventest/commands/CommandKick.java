package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "kick")
@Arguments(arguments = "<PlayerName> [Reason...]")
@Description(description = "")
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
            sender.sendMessage("§cPlayer not found!");
            return;
        }

        player.playerNetServerHandler.kickPlayer(reason);

        if (reason.length() > 0) {
            MinestarCommandHandler.notifyAdmins(sender, "commands.kick.success.reason", new Object[]{player.getEntityName(), reason});
        } else {
            MinestarCommandHandler.notifyAdmins(sender, "commands.kick.success", new Object[]{player.getEntityName()});
        }
    }
}
