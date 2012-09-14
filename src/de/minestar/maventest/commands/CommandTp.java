package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NumberInvalidException;
import net.minecraft.src.PlayerNotFoundException;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "tp")
// TODO: What kind of arguments?
@Arguments(arguments = "")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters.")
public class CommandTp extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer server = MinecraftServer.getServer();
        EntityPlayerMP player;

        if (argumentList.length() != 2 && argumentList.length() != 4) {
            player = (EntityPlayerMP) getCommandSenderAsPlayer(sender);
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
                MinestarCommandHandler.notifyAdmins(sender, "commands.tp.success", player.getEntityName(), player2.getEntityName());
            } else if (player.worldObj != null) {
                int i = argumentList.length() - 3;
                int max = 30000000;
                int x = parseIntBounded(sender, argumentList.getString(i++), -max, max);
                int y = parseIntBounded(sender, argumentList.getString(i++), 0, 256);
                int z = parseIntBounded(sender, argumentList.getString(i++), -max, max);
                player.setPositionAndUpdate((double) ((float) x + 0.5F), (double) y, (double) ((float) z + 0.5F));
                MinestarCommandHandler.notifyAdmins(sender, "commands.tp.coordinates", player.getEntityName(), x, y, z);
            }

        }

    }

    /**
     * Parses an int from the given string.
     */
    private int parseInt(ICommandSender par0ICommandSender, String par1Str) {
        try {
            return Integer.parseInt(par1Str);
        } catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{par1Str});
        }
    }

    /**
     * Parses an int from the given string within a specified bound.
     */
    private int parseIntBounded(ICommandSender par0ICommandSender, String par1Str, int par2, int par3) {
        int var4 = parseInt(par0ICommandSender, par1Str);

        if (var4 < par2) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{Integer.valueOf(var4), Integer.valueOf(par2)});
        } else if (var4 > par3) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{Integer.valueOf(var4), Integer.valueOf(par3)});
        } else {
            return var4;
        }
    }

    /**
     * Returns the given ICommandSender as a EntityPlayer or throw an exception.
     */
    private EntityPlayer getCommandSenderAsPlayer(ICommandSender par0ICommandSender) {
        if (par0ICommandSender instanceof EntityPlayer) {
            return (EntityPlayer) par0ICommandSender;
        } else {
            throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
        }
    }

}
