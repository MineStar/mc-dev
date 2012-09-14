package de.minestar.maventest.commands.whitelist;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "add")
@Arguments(arguments = "<Player>")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters.")
public class CommandWhitelistAdd extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer.getServer().getConfigurationManager().addToWhiteList(argumentList.getString(0));
        MinestarCommandHandler.notifyAdmins(sender, "commands.whitelist.add.success", argumentList.getString(0));
    }

}
