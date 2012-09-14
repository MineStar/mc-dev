package de.minestar.commands.time;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WorldServer;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "add")
@Arguments(arguments = "<Time>")
public class CommandTimeAdd extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        int time = parseIntWithMin(sender, argumentList.getString(0), 0);
        this.addTime(sender, time);
        CommandHandler.notifyAdmins(sender, "commands.time.added", time);

    }

    protected void addTime(ICommandSender par1ICommandSender, int par2) {
        for (int i = 0; i < MinecraftServer.getServer().theWorldServer.length; ++i) {
            WorldServer worldServer = MinecraftServer.getServer().theWorldServer[i];
            worldServer.setTime(worldServer.getWorldTime() + (long) par2);
        }
    }
}
