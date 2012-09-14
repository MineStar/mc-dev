package de.minestar.maventest.commandsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.IAdminCommand;
import net.minecraft.src.ICommand;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commands.CommandKick;

public class MinestarCommandHandler implements IAdminCommand {

    public HashMap<String, AbstractCommand> registeredCommands;

    private static MinestarCommandHandler INSTANCE;

    /**
     * Constructor. The given pluginname will be used in the registered commands.
     * 
     * @param pluginName
     */
    public MinestarCommandHandler() {
        this.registeredCommands = new HashMap<String, AbstractCommand>();
        this.registerCommand(new CommandKick());
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
                var6.sendMessage("\u00a77\u00a7o[" + par1ICommandSender.getCommandSenderName() + ": " + var6.translateString(par3Str, par4ArrayOfObj) + "]");
            }
        }

        if (par1ICommandSender != MinecraftServer.getServer()) {
            MinecraftServer.logger.info("[" + par1ICommandSender.getCommandSenderName() + ": " + MinecraftServer.getServer().translateString(par3Str, par4ArrayOfObj) + "]");
        }

        if ((par2 & 1) != 1) {
            par1ICommandSender.sendMessage(par1ICommandSender.translateString(par3Str, par4ArrayOfObj));
        }
    }

    public List<String> getTabCompletionOptions(ICommandSender par1ICommandSender, String par2Str) {
        String[] var3 = par2Str.split(" ", -1);
        String var4 = var3[0];

        if (var3.length == 1) {
            ArrayList<String> var8 = new ArrayList<String>();
            Iterator<Entry<String, AbstractCommand>> var6 = this.registeredCommands.entrySet().iterator();

            while (var6.hasNext()) {
                Entry<String, AbstractCommand> var7 = var6.next();

                if (AbstractCommand.doesStringStartWith(var4, (String) var7.getKey()) && ((AbstractCommand) var7.getValue()).canCommandSenderUseCommand(par1ICommandSender)) {
                    var8.add(var7.getKey());
                }
            }

            return var8;
        } else {
            // if (var3.length > 1) {
            // ICommand var5 = (ICommand) this.field_71562_a.get(var4);
            //
            // if (var5 != null) {
            // return var5.addTabCompletionOptions(par1ICommandSender, t(var3));
            // }
            // }

            return null;
        }
    }
}
