package de.minestar.commands.time;

import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "time")
@Arguments(arguments = "")
public class CommandTime extends AbstractCommand {

    @Override
    protected void createSubCommands() {
        this.registerCommand(new CommandTimeAdd());
        this.registerCommand(new CommandTimeSet());
    }

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        // DO NOTHING - SUPER COMMAND

    }

}
