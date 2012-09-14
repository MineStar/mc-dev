package de.minestar.maventest.commands.whitelist;

import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "whitelist")
@Arguments(arguments = "")
public class CommandWhitelist extends AbstractCommand {

    @Override
    protected void createSubCommands() {
        this.registerCommand(new CommandWhitelistAdd());
        this.registerCommand(new CommandWhitelistList());
        this.registerCommand(new CommandWhitelistOff());
        this.registerCommand(new CommandWhitelistOn());
        this.registerCommand(new CommandWhitelistReload());
        this.registerCommand(new CommandWhitelistRemove());
    }

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        // DO NOTHING - IS SUPERCOMMAND
    }

}
