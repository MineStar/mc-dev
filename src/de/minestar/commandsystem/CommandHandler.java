package de.minestar.commandsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.IAdminCommand;
import net.minecraft.src.ICommandSender;
import de.minestar.commands.CommandBan;
import de.minestar.commands.CommandGameMode;
import de.minestar.commands.CommandKick;
import de.minestar.commands.CommandKill;
import de.minestar.commands.CommandList;
import de.minestar.commands.CommandMessage;
import de.minestar.commands.CommandOp;
import de.minestar.commands.CommandPardon;
import de.minestar.commands.CommandPardonIp;
import de.minestar.commands.CommandPublishLocal;
import de.minestar.commands.CommandSaveAll;
import de.minestar.commands.CommandSaveOff;
import de.minestar.commands.CommandSaveOn;
import de.minestar.commands.CommandSay;
import de.minestar.commands.CommandShowSeed;
import de.minestar.commands.CommandStop;
import de.minestar.commands.CommandToggleDownfall;
import de.minestar.commands.CommandTp;
import de.minestar.commands.CommandXP;
import de.minestar.commands.time.CommandTime;
import de.minestar.commands.whitelist.CommandWhitelist;

public class CommandHandler implements IAdminCommand {

    public HashMap<String, AbstractCommand> registeredCommands;

    private static CommandHandler INSTANCE;

    /**
     * Constructor. The given pluginname will be used in the registered commands.
     * 
     * @param pluginName
     */
    public CommandHandler() {
        this.registeredCommands = new HashMap<String, AbstractCommand>();
        this.registerCommand(new CommandBan());
        this.registerCommand(new CommandGameMode());
        this.registerCommand(new CommandKick());
        this.registerCommand(new CommandKill());
        this.registerCommand(new CommandList());
        this.registerCommand(new CommandMessage());
        this.registerCommand(new CommandOp());
        this.registerCommand(new CommandPardon());
        this.registerCommand(new CommandPardonIp());
        this.registerCommand(new CommandPublishLocal());
        this.registerCommand(new CommandSaveAll());
        this.registerCommand(new CommandSaveOff());
        this.registerCommand(new CommandSaveOn());
        this.registerCommand(new CommandSay());
        this.registerCommand(new CommandShowSeed());
        this.registerCommand(new CommandStop());
        this.registerCommand(new CommandTime());
        this.registerCommand(new CommandToggleDownfall());
        this.registerCommand(new CommandTp());
        this.registerCommand(new CommandXP());
        this.registerCommand(new CommandWhitelist());
        INSTANCE = this;
    }

    /**
     * Register a new command
     * 
     * @param command
     * @return <b>true</b> if the command is registered successfully, otherwise <b>false</b>
     */
    public boolean registerCommand(AbstractCommand command) {
        // set the pluginame

        // CHECK: is the command already registered?
        if (this.registeredCommands.containsKey(command.getLabel())) {
            throw new RuntimeException("Command '" + command.getLabel() + "' is already registered in '" + command.getCommand() + "'!");
        }

        // register the command
        this.registeredCommands.put("/" + command.getLabel(), command);

        // initialize the subcommands for the given command
        command.initializeSubCommands();
        return true;
    }

    /**
     * Handle a command for this CommandHandler
     */
    public boolean handleCommand(ICommandSender sender, String commandString) {

        String[] split = commandString.split(" ");
        String label = split[0];

        // cast the label to lowercase
        label = label.toLowerCase();

        // in this case: the label needs to start with an '/'
        if (!label.startsWith("/")) {
            label = "/" + label;
        }

        // lookup the command
        AbstractCommand command = this.registeredCommands.get(label);

        // is the command == null => command is registered by bukkit, but not in
        // the commandhandler
        if (command == null) {
            sender.sendMessage("Command not found!");
            return false;
        }

        // handle the command
        ArgumentList argumentList = new ArgumentList(split);
        argumentList = new ArgumentList(argumentList, 1);
        command.handleCommand(sender, argumentList);
        return true;
    }

    /**
     * List all registered commands for this CommandHandler
     */
    public void listCommands(ICommandSender sender) {
        for (AbstractCommand command : this.registeredCommands.values()) {
            command.listCommand(sender);
        }
    }

    public static void notifyAdmins(ICommandSender par0ICommandSender, String par1Str, Object... par2ArrayOfObj) {
        notifyOps(par0ICommandSender, 0, par1Str, par2ArrayOfObj);
    }

    public static void notifyOps(ICommandSender par0ICommandSender, int par1, String par2Str, Object... par3ArrayOfObj) {
        INSTANCE.notifyAdmins(par0ICommandSender, par1, par2Str, par3ArrayOfObj);
    }

    @Override
    public void notifyAdmins(ICommandSender par1ICommandSender, int par2, String par3Str, Object... par4ArrayOfObj) {
        Iterator<EntityPlayerMP> iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP var6 = (EntityPlayerMP) iterator.next();

            if (var6 != par1ICommandSender && MinecraftServer.getServer().getConfigurationManager().isOp(var6.username)) {
                var6.sendInfo("\u00a77\u00a7o[" + par1ICommandSender.getCommandSenderName() + ": " + var6.translateString(par3Str, par4ArrayOfObj) + "]");
            }
        }

        if (par1ICommandSender != MinecraftServer.getServer()) {
            MinecraftServer.logger.info("[" + par1ICommandSender.getCommandSenderName() + ": " + MinecraftServer.getServer().translateString(par3Str, par4ArrayOfObj) + "]");
        }

        if ((par2 & 1) != 1) {
            par1ICommandSender.sendInfo(par1ICommandSender.translateString(par3Str, par4ArrayOfObj));
        }
    }

    public List<String> getTabCompletionOptions(ICommandSender par1ICommandSender, String par2Str) {
        String[] var3 = par2Str.split(" ", -1);
        String var4 = var3[0];

        if (var3.length == 1) {
            ArrayList<String> list = new ArrayList<String>();
            Iterator<Entry<String, AbstractCommand>> var6 = this.registeredCommands.entrySet().iterator();

            while (var6.hasNext()) {
                Entry<String, AbstractCommand> var7 = var6.next();

                if (AbstractCommand.doesStringStartWith("/" + var4, var7.getKey()) && var7.getValue().canCommandSenderUseCommand(par1ICommandSender)) {
                    list.add(var7.getKey().substring(1));
                }
            }

            return list;
        } else {
            if (var3.length > 1) {
                AbstractCommand var5 = this.registeredCommands.get("/" + var4);

                if (var5 != null) {
                    return var5.addTabCompletionOptions(par1ICommandSender, createArguments(var3));
                }
            }

            return null;
        }
    }

    private static String[] createArguments(String[] par0ArrayOfStr) {
        String[] var1 = new String[par0ArrayOfStr.length - 1];

        for (int var2 = 1; var2 < par0ArrayOfStr.length; ++var2) {
            var1[var2 - 1] = par0ArrayOfStr[var2];
        }

        return var1;
    }

}
