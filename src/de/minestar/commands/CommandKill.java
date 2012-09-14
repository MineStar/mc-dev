package de.minestar.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "kill")
@Arguments(arguments = "[PlayerName...]")
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
                    continue;
                }

                // ATTACK PLAYER
                target.attackEntityFrom(DamageSource.outOfWorld, 1000);
                sender.sendMessage("Ouch. That look like it hurt.");
            }
        } else {
            target = this.getCommandSenderAsPlayer(sender);

            if (target == null) {
                sender.sendError("Player '" + sender.getCommandSenderName() + "' not found!");
                return;
            }
        }

        // ATTACK PLAYER
        target.attackEntityFrom(DamageSource.outOfWorld, 1000);
        sender.sendInfo("Ouch. That look like it hurt.");
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            String var3 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList<String> var4 = new ArrayList<String>();
            String[] var5 = MinecraftServer.getServer().getPlayerNamesAsList();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String var8 = var5[var7];

                if (doesStringStartWith(var3, var8)) {
                    var4.add(var8);
                }
            }

            return var4;
        } else {
            return null;
        }
    }
}
