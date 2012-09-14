package de.minestar.maventest.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Description;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "tell")
@Arguments(arguments = "<PlayerName> <Message> [...]")
@Description(description = "This is a normal command. It is a single command with 1 needed parameter and endless optional parameters.")
public class CommandMessage extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {
        EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(argumentList.getString(0));
        if (target == null) {
            sender.sendMessage("Player not found");
        } else if (target == sender) {
            sender.sendMessage("You can't whisper yourself!");
        } else {
            String text = getText(argumentList);
            target.sendMessage("\u00a77\u00a7o" + target.translateString("commands.message.display.incoming", new Object[]{sender.getCommandSenderName(), text}));
            sender.sendMessage("\u00a77\u00a7o" + sender.translateString("commands.message.display.outgoing", new Object[]{target.getCommandSenderName(), text}));
        }
    }

    private String getText(ArgumentList argumentList) {
        StringBuilder sBuilder = new StringBuilder();
        for (String s : argumentList.getIterator(String.class, 1)) {
            sBuilder.append(s);
            sBuilder.append(' ');
        }

        return sBuilder.toString();
    }
}
