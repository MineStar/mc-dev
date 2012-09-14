package de.minestar.maventest.commands.time;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WorldServer;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "add")
@Arguments(arguments = "<Time>")
public class CommandTimeAdd extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        int time = parseIntWithMin(sender, argumentList.getString(0), 0);
        this.addTime(sender, time);
        MinestarCommandHandler.notifyAdmins(sender, "commands.time.added", time);

    }

    protected void addTime(ICommandSender par1ICommandSender, int par2) {
        for (int i = 0; i < MinecraftServer.getServer().theWorldServer.length; ++i) {
            WorldServer var4 = MinecraftServer.getServer().theWorldServer[i];
            var4.setTime(var4.getWorldTime() + (long) par2);
        }
    }
}
