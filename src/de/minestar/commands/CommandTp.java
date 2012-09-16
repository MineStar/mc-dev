package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.CommandUtils;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "tp")
@Arguments(arguments = "<ToPlayer|X> [<PlayerToTeleport|Y> [Z]]")
public class CommandTp extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer server = MinecraftServer.getServer();
        EntityPlayerMP senderPlayer;

        if (argumentList.length() != 2 && argumentList.length() != 4) {
            senderPlayer = CommandUtils.getCommandSenderAsPlayer(sender);
        } else {
            senderPlayer = server.getConfigurationManager().getPlayerEntity(argumentList.getString(0));
            if (senderPlayer == null)
                throw new PlayerNotFoundException();
        }

        if (argumentList.length() != 3 && argumentList.length() != 4) {
            if (argumentList.length() == 1 || argumentList.length() == 2) {
                // teleport player to player
                EntityPlayerMP targetPlayer = server.getConfigurationManager().getPlayerEntity(argumentList.getString(argumentList.length() - 1));

                if (targetPlayer == null)
                    throw new PlayerNotFoundException();

                senderPlayer.playerNetServerHandler.teleportTo(targetPlayer.posX, targetPlayer.posY, targetPlayer.posZ, targetPlayer.rotationYaw, targetPlayer.rotationPitch);
                CommandHandler.notifyAdmins(sender, "commands.tp.success", senderPlayer.getEntityName(), targetPlayer.getEntityName());
            } else if (senderPlayer.worldObj != null) {
                // teleport player to coordinates
                int i = argumentList.length() - 3;
                int max = 30000000;
                int x = CommandUtils.parseIntBounded(sender, argumentList.getString(i++), -max, max);
                int y = CommandUtils.parseIntBounded(sender, argumentList.getString(i++), 0, 256);
                int z = CommandUtils.parseIntBounded(sender, argumentList.getString(i++), -max, max);
                senderPlayer.setPositionAndUpdate((double) ((float) x + 0.5F), (double) y, (double) ((float) z + 0.5F));
                CommandHandler.notifyAdmins(sender, "commands.tp.coordinates", senderPlayer.getEntityName(), x, y, z);
            }
        }

    }
}
