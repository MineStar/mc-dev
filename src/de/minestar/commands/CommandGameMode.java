package de.minestar.commands;

import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.StatCollector;
import de.minestar.commandsystem.AbstractCommand;
import de.minestar.commandsystem.ArgumentList;
import de.minestar.commandsystem.CommandHandler;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.Label;

@Label(label = "gamemode")
@Arguments(arguments = "0|1|2|classic|creative|adventure [<PlayerName>]")
public class CommandGameMode extends AbstractCommand {

    private EnumGameType getGameType(String argument) {
        for (EnumGameType gameType : EnumGameType.values()) {
            if (String.valueOf(gameType.getID()).equals(argument) || gameType.getName().equals(argument)) {
                return gameType;
            }
        }
        return EnumGameType.NOT_SET;
    }

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        // GET GAME-TYPE BY ARGUMENT 0
        EnumGameType gameType = this.getGameType(argumentList.getString(0));
        if (gameType.equals(EnumGameType.NOT_SET)) {
            sender.sendError("GameType '" + argumentList.getString(0) + "' is not valid!");
            return;
        }

        // GET PLAYER, IF ARGUMENTSIZE > 1
        EntityPlayer target = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(sender.getCommandSenderName());

        if (argumentList.length() > 1) {
            target = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(argumentList.getString(1));
            // is the player found?
            if (target == null) {
                sender.sendError("Player '" + argumentList.getString(1) + "' not found!");
                return;
            }
        } else {
            // is the player found?
            if (target == null) {
                sender.sendError("Player '" + sender.getCommandSenderName() + "' not found!");
                return;
            }
        }

        // update the gametype
        target.setGameType(gameType);

        // send locale message
        String localeText = StatCollector.translateToLocal("gameMode." + gameType.getName());
        if (sender != target) {
            CommandHandler.notifyOps(sender, 1, "commands.gamemode.success.other", new Object[]{target.getEntityName(), localeText});
        } else {
            CommandHandler.notifyOps(sender, 1, "commands.gamemode.success.self", new Object[]{localeText});
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Override
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[]{"survival", "creative", "adventure"}) : (par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getPlayerList()) : null);
    }

    protected String[] getPlayerList() {
        return MinecraftServer.getServer().getPlayerNamesAsList();
    }
}
