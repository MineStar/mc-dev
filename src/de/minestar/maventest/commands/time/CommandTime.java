package de.minestar.maventest.commands.time;

import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

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
