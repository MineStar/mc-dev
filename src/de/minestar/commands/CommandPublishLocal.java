package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.MinestarCommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "publish")
@Arguments(arguments = "")
public class CommandPublishLocal extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        String mode = MinecraftServer.getServer().func_71206_a(EnumGameType.SURVIVAL, false);
        if (mode != null)
            MinestarCommandHandler.notifyAdmins(sender, "commands.publish.started", mode);
        else
            MinestarCommandHandler.notifyAdmins(sender, "commands.publish.failed");

    }

}
