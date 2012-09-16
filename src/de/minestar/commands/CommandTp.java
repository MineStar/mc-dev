package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.ParseUtils;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "tp")
// TODO: What kind of arguments?
@Arguments(arguments = "")
public class CommandTp extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer server = MinecraftServer.getServer();
        EntityPlayerMP player;

        if (argumentList.length() != 2 && argumentList.length() != 4) {
            player = ParseUtils.getCommandSenderAsPlayer(sender);
        } else {
            player = server.getConfigurationManager().getPlayerEntity(argumentList.getString(0));
            if (player == null)
                throw new PlayerNotFoundException();
        }

        if (argumentList.length() != 3 && argumentList.length() != 4) {
            if (argumentList.length() == 1 || argumentList.length() == 2) {
                EntityPlayerMP player2 = server.getConfigurationManager().getPlayerEntity(argumentList.getString(argumentList.length() - 1));

                if (player2 == null)
                    throw new PlayerNotFoundException();

                player.playerNetServerHandler.teleportTo(player2.posX, player2.posY, player2.posZ, player2.rotationYaw, player2.rotationPitch);
                CommandHandler.notifyAdmins(sender, "commands.tp.success", player.getEntityName(), player2.getEntityName());
            } else if (player.worldObj != null) {
                int i = argumentList.length() - 3;
                int max = 30000000;
                int x = ParseUtils.parseIntBounded(sender, argumentList.getString(i++), -max, max);
                int y = ParseUtils.parseIntBounded(sender, argumentList.getString(i++), 0, 256);
                int z = ParseUtils.parseIntBounded(sender, argumentList.getString(i++), -max, max);
                player.setPositionAndUpdate((double) ((float) x + 0.5F), (double) y, (double) ((float) z + 0.5F));
                CommandHandler.notifyAdmins(sender, "commands.tp.coordinates", player.getEntityName(), x, y, z);
            }
        }

    }
}
