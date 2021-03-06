package de.minestar.commandsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.src.ICommandSender;
import de.minestar.commandsystem.annotations.Alias;
import de.minestar.commandsystem.annotations.Arguments;
import de.minestar.commandsystem.annotations.ExecuteSuperCommand;
import de.minestar.commandsystem.annotations.Label;

public abstract class AbstractCommand {

    // String for no permissions
    public final static String NO_PERMISSION = "You are not allowed to use this command!";

    // vars for the handling of supercommands
    private boolean superCommand = false; // true = this command has subcommands
                                          // ; false = this command has no
                                          // subcommands
    private boolean executeSuperCommand = false; // true = execute this
                                                 // supercommand ; false = only
                                                 // print the syntax

    // the parent command (null, if this command doesn't have a parent)
    private AbstractCommand parentCommand;

    // needed vars for each command
    private final String commandLabel;
    private String[] aliases;
    private String arguments;

    // list of subcommands
    public HashMap<String, AbstractCommand> subCommands = new HashMap<String, AbstractCommand>();
    private SyntaxTree syntaxTree;

    /**
     * Constructor
     */
    public AbstractCommand() {
        // set the pluginName & the parent command
        this.parentCommand = null;

        // fetch the annotations for this command
        Label labelAnnotation = this.getClass().getAnnotation(Label.class);
        Alias aliasAnnotation = this.getClass().getAnnotation(Alias.class);
        Arguments argumentAnnotation = this.getClass().getAnnotation(Arguments.class);

        // Save the label. NOTE: If the label is not set, this will throw an
        // RuntimeException and the command won't be registered.
        if (labelAnnotation == null) {
            this.commandLabel = "";
            throw new RuntimeException("Could not create command '" + this.getClass().getSimpleName() + "'! Commandlabel is missing!");
        } else {
            // replace all SPACES
            this.commandLabel = labelAnnotation.label().replace(" ", "");

            // do we have at least one char?
            if (this.commandLabel.length() < 1) {
                throw new RuntimeException("Could not create command '" + this.getClass().getSimpleName() + "'! Commandlabel is missing!");
            }
        }

        // get the aliases
        if (aliasAnnotation == null) {
            this.aliases = new String[0];
        } else {
            this.aliases = aliasAnnotation.aliases();
        }

        // get the argument
        if (argumentAnnotation == null) {
            this.arguments = "";
        } else {
            this.arguments = argumentAnnotation.arguments();
        }
        this.syntaxTree = new SyntaxTree(this.arguments);

        // get the execution-params
        this.executeSuperCommand = this.getClass().isAnnotationPresent(ExecuteSuperCommand.class);
    }

    /**
     * @param player
     * @return True, if the sender has enough permission to use the command Or the permissionnode is empty, so everybody can use it
     */
    protected final boolean hasPermission(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(this.getLabel());
    }

    /**
     * Check if the argumentcount was correct for this command
     * 
     * @param arguments
     * @return <b>true</b> if it is correct, otherwise <b>false</b>
     */
    public final boolean isSyntaxCorrect(ArgumentList argumentList) {
        return this.syntaxTree.checkSyntax(argumentList);
    }

    /**
     * Execute the command. This will also check the permissions for players.
     * 
     * @param sender
     * @param arguments
     */
    public final void run(ICommandSender sender, ArgumentList argumentList) {

        // check the permission
        if (!hasPermission(sender)) {
            sender.sendMessage(NO_PERMISSION);
            return;
        }

        this.execute(sender, argumentList);
    }

    /**
     * Execute the command for a player.
     * 
     * @param console
     * @param arguments
     */
    public abstract void execute(ICommandSender sender, ArgumentList argumentList);

    /**
     * This method is automatically called on registration of the command. If we have subcommands, this is the place to register them in our commands.
     */
    protected void createSubCommands() {
    }

    /**
     * This method will initialize the subcommands for this command. This method is automatically called.
     */
    public final void initializeSubCommands() {
        this.createSubCommands();
        this.superCommand = (this.subCommands.size() > 0);
    }

    /**
     * Should this Command be executed (only if it is a supercommand)
     * 
     * @return <b>true</b> if the command should be executed, otherwise <b>false</b>
     */
    public final boolean isExecuteSuperCommand() {
        return executeSuperCommand;
    }

    /**
     * Is this command a supercommand?
     * 
     * @return <b>true</b> if the command is a supercommand, <b>false</b> if it is a normal command
     */
    public final boolean isSuperCommand() {
        return superCommand;
    }

    /**
     * Set the parent of this command
     * 
     * @param command
     */
    private final void setParentCommand(AbstractCommand command) {
        this.parentCommand = command;
    }

    /**
     * Update the subcommands of this command. This method is called automatically.
     */
    public final void updateSubCommands() {
        for (AbstractCommand command : this.subCommands.values()) {
            command.setParentCommand(this);
        }
    }

    /**
     * Get the label of this command.
     * 
     * @return the label
     */
    public final String getLabel() {
        return commandLabel;
    }

    /**
     * Get the aliases of this command.
     * 
     * @return the aliases
     */
    public final String[] getAliases() {
        return aliases;
    }

    /**
     * Get all subcommands
     * 
     * @return all subcommands
     */
    public final ArrayList<AbstractCommand> getSubCommands() {
        if (this.subCommands != null) {
            return new ArrayList<AbstractCommand>(subCommands.values());
        } else {
            return new ArrayList<AbstractCommand>();
        }
    }

    /**
     * Get the argumentstring.
     * 
     * @return the argumentstring
     */
    public final String getArguments() {
        return arguments;
    }

    /**
     * Get the syntax without the arguments (but with all supercommands) for this command.
     * 
     * @return the syntax without the arguments, but with all supercommands
     */
    public final String getCommand() {
        // iterate over every parent and add the label in front of the current
        // syntax
        String syntax = this.getLabel();
        AbstractCommand parent = this.parentCommand;
        while (parent != null) {
            syntax = parent.getLabel() + " " + syntax;
            parent = parent.parentCommand;
        }
        return syntax;
    }

    /**
     * Get the complete syntax for this command.
     * 
     * @return the complete syntax (including supercommands and the argumentstring)
     */
    public final String getCommandWithSyntax() {
        // iterate over every parent and add the label in front of the current
        // syntax
        String syntax = "/" + this.getLabel();
        AbstractCommand parent = this.parentCommand;
        while (parent != null) {
            syntax = "/" + parent.getLabel() + " " + syntax;
            parent = parent.parentCommand;
        }
        syntax += " " + this.getArguments();
        return syntax;
    }

    /**
     * Register a new subcommand for this command.
     * 
     * @param command
     * @return <b>true</b> if the command is registered successfully, otherwise <b>false</b>
     */
    protected final boolean registerCommand(AbstractCommand command) {
        // create the map, if it is null
        if (this.subCommands == null) {
            this.subCommands = new HashMap<String, AbstractCommand>();
        }

        // is a subcommand with this label already registered?
        if (this.subCommands.containsKey(command.getLabel())) {
            throw new RuntimeException("Command '" + command.getLabel() + "' is already registered in '" + command.getCommand() + "'!");
        }

        // store the command
        this.subCommands.put(command.getLabel(), command);

        // register aliases
        String[] aliases = command.getAliases();
        for (String alias : aliases) {
            // CHECK: is the command already registered?
            if (this.subCommands.containsKey(alias)) {
                throw new RuntimeException("Alias '" + alias + "' is already registered!");
            }

            // register the command
            this.subCommands.put(alias, command);
        }

        // finally update the command and initialize it
        command.setParentCommand(this);
        command.initializeSubCommands();

        return true;
    }

    /**
     * Print the syntax for this command.
     */
    public final void printWrongSyntax(ICommandSender sender) {
        sender.sendError("Wrong syntax!");
        this.listCommand(sender);
    }
    /**
     * List the command
     */
    public final void listCommand(ICommandSender sender) {
        if (this.isSuperCommand()) {
            sender.sendInfo("Possible Subcommands for: " + this.getLabel());
            sender.sendInfo(this.getCommandWithSyntax());
        } else {
            sender.sendInfo(this.getCommandWithSyntax());
        }

        ArrayList<AbstractCommand> subCommands = getSubCommands();
        for (AbstractCommand subCommand : subCommands) {
            sender.sendInfo(subCommand.getCommandWithSyntax());
        }
    }

    public final boolean handleCommand(ICommandSender sender, ArgumentList argumentList) {
        // lookup a possible subcommand
        AbstractCommand subCommand = null;
        if (this.subCommands.size() > 0 && argumentList.length() > 0) {
            subCommand = this.subCommands.get(argumentList.getString(0));
        }

        if (subCommand != null) {
            // we have a subcommand, so handle it

            // get the new argumentlist
            argumentList = new ArgumentList(argumentList, 1);
            return subCommand.handleCommand(sender, argumentList);
        } else {
            // get the new argumentlist

            // no subcommand, check syntax and try to execute it
            if (this.isSuperCommand()) {
                if (this.isExecuteSuperCommand()) {
                    // we have a supercommand that should be executed

                    // check argumentcount & try to execute it
                    if (this.isSyntaxCorrect(argumentList)) {
                        // (2.1)

                        // execute the command
                        this.run(sender, argumentList);
                        return true;
                    } else {
                        // (2.2)

                        // print the syntax
                        this.printWrongSyntax(sender);
                        return false;
                    }
                } else {
                    // print the syntax
                    this.listCommand(sender);
                    return false;
                }
            } else {
                // NO SUPER COMMAND, JUST TRY TO EXECUTE

                // check argumentcount & try to execute it
                if (this.isSyntaxCorrect(argumentList)) {
                    // (2.1)

                    // execute the command
                    this.run(sender, argumentList);
                    return true;
                } else {
                    // (2.2)

                    // print the syntax
                    this.printWrongSyntax(sender);
                    return false;
                }
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return null;
    }

}
