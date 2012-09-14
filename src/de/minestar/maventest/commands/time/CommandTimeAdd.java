package de.minestar.maventest.commands.time;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NumberInvalidException;
import net.minecraft.src.WorldServer;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "add")
@Arguments(arguments = "<Time>")
@Description(description = "")
public class CommandTimeAdd extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        int time = parseIntWithMin(sender, argumentList.getString(0), 0);
        this.addTime(sender, time);
        MinestarCommandHandler.notifyAdmins(sender, "commands.time.added", time);

    }
    /**
     * Parses an int from the given sring with a specified minimum.
     */
    private int parseIntWithMin(ICommandSender par0ICommandSender, String par1Str, int par2) {
        return parseIntBounded(par0ICommandSender, par1Str, par2, Integer.MAX_VALUE);
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
     * Parses an int from the given string.
     */
    private int parseInt(ICommandSender par0ICommandSender, String par1Str) {
        try {
            return Integer.parseInt(par1Str);
        } catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{par1Str});
        }
    }

    protected void addTime(ICommandSender par1ICommandSender, int par2) {

        for (int i = 0; i < MinecraftServer.getServer().theWorldServer.length; ++i) {
            WorldServer var4 = MinecraftServer.getServer().theWorldServer[i];
            var4.setTime(var4.getWorldTime() + (long) par2);
        }
    }
}
