package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.WorldServer;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.MinestarCommandHandler;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

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
            MinestarCommandHandler.notifyAdmins(sender, "commands.save.failed", e.getMessage());
            return;
        }

        MinestarCommandHandler.notifyAdmins(sender, "commands.save.success");
    }

}
