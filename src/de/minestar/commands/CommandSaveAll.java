package de.minestar.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.WorldServer;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "save-all")
@Arguments(arguments = "")
public class CommandSaveAll extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        MinecraftServer server = MinecraftServer.getServer();
        sender.sendMessage(sender.translateString("commands.save.start"));

        if (server.getConfigurationManager() != null)
            server.getConfigurationManager().savePlayerStates();

        try {
            for (int i = 0; i < server.theWorldServer.length; ++i) {
                if (server.theWorldServer[i] != null) {
                    WorldServer worldServer = server.theWorldServer[i];
                    boolean levelSaving = worldServer.levelSaving;
                    worldServer.levelSaving = false;
                    worldServer.saveAllChunks(true, null);
                    worldServer.levelSaving = levelSaving;
                }
            }
        } catch (MinecraftException e) {
            CommandHandler.notifyAdmins(sender, "commands.save.failed", e.getMessage());
            return;
        }

        CommandHandler.notifyAdmins(sender, "commands.save.success");
    }

}
