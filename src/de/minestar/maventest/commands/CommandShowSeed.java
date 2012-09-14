package de.minestar.maventest.commands;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "seed")
@Arguments(arguments = "")
@Description(description = "")
public class CommandShowSeed extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        sender.sendMessage("Seed: " + player.worldObj.getSeed());
    }

    /**
     * Returns the given ICommandSender as a EntityPlayer or throw an exception.
     */
    private EntityPlayer getCommandSenderAsPlayer(ICommandSender par0ICommandSender) {
        if (par0ICommandSender instanceof EntityPlayer) {
            return (EntityPlayer) par0ICommandSender;
        } else {
            throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
        }
    }
}
