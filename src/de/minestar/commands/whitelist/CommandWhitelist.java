package de.minestar.commands.whitelist;

import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

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
