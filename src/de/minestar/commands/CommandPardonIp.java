package de.minestar.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.SyntaxErrorException;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "pardon-id")
@Arguments(arguments = "<IP-Address>")
public class CommandPardonIp extends AbstractCommand {

    private final static Pattern PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        Matcher matcher = PATTERN.matcher(argumentList.getString(0));

        if (matcher.matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeBan(argumentList.getString(0));
            CommandHandler.notifyAdmins(sender, "commands.unbanip.success", argumentList.getString(0));
        } else {
            throw new SyntaxErrorException("commands.unbanip.invalid");
        }
    }
}
