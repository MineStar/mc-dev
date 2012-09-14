package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "kill")
@Arguments(arguments = "[PlayerName...]")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters.")
public class CommandKill extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        EntityPlayerMP target = null;
        if (argumentList.length() > 0) {
            for (String playerName : argumentList.getIterator(String.class, 0)) {
                // find target
                target = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);
                if (target == null) {
                    sender.sendMessage("§cPlayer '" + playerName + "' not found!");
                    return;
                }

                // ATTACK PLAYER
                target.attackEntityFrom(DamageSource.outOfWorld, 1000);
                sender.sendMessage("Ouch. That look like it hurt.");
            }
        } else {
            target = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(sender.getCommandSenderName());

            if (target == null) {
                sender.sendMessage("§cPlayer '" + sender.getCommandSenderName() + "' not found!");
                return;
            }
        }

        // ATTACK PLAYER
        target.attackEntityFrom(DamageSource.outOfWorld, 1000);
        sender.sendMessage("Ouch. That look like it hurt.");
    }
}
