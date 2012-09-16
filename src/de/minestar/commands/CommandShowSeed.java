package de.minestar.commands;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandUtils;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "seed")
@Arguments(arguments = "")
public class CommandShowSeed extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        EntityPlayerMP player = CommandUtils.getCommandSenderAsPlayer(sender);
        sender.sendInfo("Seed: " + player.worldObj.getSeed());
    }
}
