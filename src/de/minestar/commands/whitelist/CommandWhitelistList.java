package de.minestar.commands.whitelist;

import java.util.Set;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandUtils;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "list")
@Arguments(arguments = "")
public class CommandWhitelistList extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        Set<String> whiteList = MinecraftServer.getServer().getConfigurationManager().getWhiteListedIPs();
        sender.sendInfo(sender.translateString("commands.whitelist.list", whiteList.size(), MinecraftServer.getServer().getConfigurationManager().func_72373_m().length));
        sender.sendInfo(CommandUtils.joinNiceString(whiteList.toArray()));

    }

}
