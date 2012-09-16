package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WorldServer;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.CommandUtils;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "time")
@Arguments(arguments = "SET|ADD <Time>")
public class CommandTime extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        if (argumentList.getString(0).equalsIgnoreCase("ADD")) {
            // ADD THE TIME
            int time = CommandUtils.parseIntWithMin(sender, argumentList.getString(0), 0);
            this.addTimeInAllWorlds(time);
            CommandHandler.notifyAdmins(sender, "commands.time.added", time);
        } else {
            // SET THE TIME
            int time = 0;
            String timeString = argumentList.getString(0).toLowerCase();
            if (timeString.equalsIgnoreCase("day"))
                time = 0;
            else if (timeString.equalsIgnoreCase("night"))
                time = 12500;
            else
                time = CommandUtils.parseIntWithMin(sender, timeString, 0);

            this.setTimeInAllWorlds(time);
            CommandHandler.notifyAdmins(sender, "commands.time.set", time);
        }
    }

    protected void addTimeInAllWorlds(int time) {
        for (int i = 0; i < MinecraftServer.getServer().theWorldServer.length; ++i) {
            WorldServer worldServer = MinecraftServer.getServer().theWorldServer[i];
            worldServer.setTime(worldServer.getWorldTime() + (long) time);
        }
    }

    protected void setTimeInAllWorlds(int time) {
        for (int index = 0; index < MinecraftServer.getServer().theWorldServer.length; ++index) {
            MinecraftServer.getServer().theWorldServer[index].setTime((long) time);
        }
    }

}
