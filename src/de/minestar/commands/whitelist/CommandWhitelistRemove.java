package de.minestar.commands.whitelist;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.MinestarCommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "remove")
@Arguments(arguments = "<Player>")
public class CommandWhitelistRemove extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().getConfigurationManager().removeFromWhitelist(argumentList.getString(0));
        MinestarCommandHandler.notifyAdmins(sender, "commands.whitelist.remove.success", argumentList.getString(0));
    }

}
