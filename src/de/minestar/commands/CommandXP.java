package de.minestar.commands;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.MinestarCommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "xp")
@Arguments(arguments = "")
public class CommandXP extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        int xp = parseIntBounded(sender, argumentList.getString(0), 0, 5000);
        EntityPlayer player;

        if (argumentList.length() > 1) {
            player = this.getPlayerByName(argumentList.getString(1));
        } else {
            player = getCommandSenderAsPlayer(sender);
        }

        player.addExperience(xp);
        MinestarCommandHandler.notifyAdmins(sender, "commands.xp.success", xp, player.getEntityName());
    }
}
