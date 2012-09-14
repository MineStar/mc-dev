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

@Label(label = "xp")
@Arguments(arguments = "")
@Description(description = "")
public class CommandXP extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        int xp = parseIntBounded(sender, argumentList.getString(0), 0, 5000);
        EntityPlayer player;

        if (argumentList.length() > 1) {
            player = this.func_71543_a(argumentList.getString(1));
        } else {
            player = getCommandSenderAsPlayer(sender);
        }

        player.addExperience(xp);
        MinestarCommandHandler.notifyAdmins(sender, "commands.xp.success", xp, player.getEntityName());

    }

    protected EntityPlayer func_71543_a(String par1Str) {
        EntityPlayerMP var2 = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(par1Str);

        if (var2 == null) {
            throw new PlayerNotFoundException();
        } else {
            return var2;
        }
    }

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

    private EntityPlayer getCommandSenderAsPlayer(ICommandSender par0ICommandSender) {
        if (par0ICommandSender instanceof EntityPlayer) {
            return (EntityPlayer) par0ICommandSender;
        } else {
            throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
        }
    }
}
