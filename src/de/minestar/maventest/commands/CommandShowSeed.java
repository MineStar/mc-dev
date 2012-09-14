package de.minestar.maventest.commands;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "seed")
@Arguments(arguments = "")
public class CommandShowSeed extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        sender.sendMessage("Seed: " + player.worldObj.getSeed());
    }
}
