package de.minestar.maventest.commands.time;

import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "time")
@Arguments(arguments = "")
@Description(description = "")
public class CommandTime extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        // DO NOTHING - SUPER COMMAND

    }

}
