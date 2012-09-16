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

@Label(label = "save")
@Arguments(arguments = "ALL|ON|OFF")
public class CommandSave extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        if (argumentList.getString(0).equalsIgnoreCase("ALL")) {
            // save all
            this.saveAll(sender, argumentList);
        } else if (argumentList.getString(0).equalsIgnoreCase("ON")) {
            // enable saving
            this.saveOn(sender, argumentList);
        } else {
            // disable saving
            this.saveOff(sender, argumentList);
        }
    }

    private void saveAll(ICommandSender sender, ArgumentList argumentList) {
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

    private void saveOn(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer server = MinecraftServer.getServer();

        for (int i = 0; i < server.theWorldServer.length; ++i) {
            if (server.theWorldServer[i] != null) {
                server.theWorldServer[i].levelSaving = false;
            }
        }

        CommandHandler.notifyAdmins(sender, "commands.save.disabled");
    }

    private void saveOff(ICommandSender sender, ArgumentList argumentList) {
        MinecraftServer server = MinecraftServer.getServer();

        for (int i = 0; i < server.theWorldServer.length; ++i) {
            if (server.theWorldServer[i] != null) {
                server.theWorldServer[i].levelSaving = true;
            }
        }

        CommandHandler.notifyAdmins(sender, "commands.save.disabled");
    }

}
