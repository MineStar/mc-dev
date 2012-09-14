package de.minestar.maventest.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.SyntaxErrorException;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "pardon-id")
@Arguments(arguments = "<IP-Address>")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters.")
public class CommandPardonIp extends AbstractCommand {

    private final static Pattern PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        Matcher matcher = PATTERN.matcher(argumentList.getString(0));

        if (matcher.matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_73709_b(argumentList.getString(0));
            MinestarCommandHandler.notifyAdmins(sender, "commands.unbanip.success", argumentList.getString(0));
        } else {
            throw new SyntaxErrorException("commands.unbanip.invalid");
        }
    }
}
