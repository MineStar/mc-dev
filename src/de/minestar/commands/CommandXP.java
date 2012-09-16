package de.minestar.commands;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.ParseUtils;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "xp")
@Arguments(arguments = "")
public class CommandXP extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        int xp = ParseUtils.parseIntBounded(sender, argumentList.getString(0), 0, 5000);
        EntityPlayerMP player;

        if (argumentList.length() > 1) {
            player = ParseUtils.getPlayerByName(argumentList.getString(1));
        } else {
            player = ParseUtils.getCommandSenderAsPlayer(sender);
        }

        player.addExperience(xp);
        CommandHandler.notifyAdmins(sender, "commands.xp.success", xp, player.getEntityName());
    }
}
