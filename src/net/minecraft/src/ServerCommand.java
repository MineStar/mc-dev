package net.minecraft.src;

public class ServerCommand {
    /** The command string. */
    public final String command;
    public final ICommandSender commandSender;

    public ServerCommand(String par1Str, ICommandSender par2ICommandSender) {
        this.command = par1Str;
        this.commandSender = par2ICommandSender;
    }
}
