package net.minecraft.src;

public interface ICommandSender {
    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    String getCommandSenderName();

    void sendInfo(String message);

    void sendError(String message);

    void sendMessage(String message);

    void sendSuccess(String message);

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    boolean canCommandSenderUseCommand(String var1);

    /**
     * Translates and formats the given string key with the given arguments.
     */
    String translateString(String var1, Object... var2);
}
