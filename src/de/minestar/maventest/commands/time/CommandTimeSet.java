package de.minestar.maventest.commands.time;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "set")
@Arguments(arguments = "<Time>")
public class CommandTimeSet extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        int time = 0;
        String timeString = argumentList.getString(0).toLowerCase();
        if (timeString.equalsIgnoreCase("day"))
            time = 0;
        else if (timeString.equalsIgnoreCase("night"))
            time = 12500;
        else
            time = parseIntWithMin(sender, timeString, 0);

        setTimeInAllWorlds(sender, time);
        MinestarCommandHandler.notifyAdmins(sender, "commands.time.set", time);

    }

    protected void setTimeInAllWorlds(ICommandSender par1ICommandSender, int par2) {
        for (int index = 0; index < MinecraftServer.getServer().theWorldServer.length; ++index) {
            MinecraftServer.getServer().theWorldServer[index].setTime((long) par2);
        }
    }
}
