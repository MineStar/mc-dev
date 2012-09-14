package de.minestar.maventest.commands.whitelist;

import java.util.Set;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ICommandSender;
import de.minestar.maventest.commandsystem.AbstractCommand;
import de.minestar.maventest.commandsystem.ArgumentList;
import de.minestar.maventest.commandsystem.annotations.Arguments;
import de.minestar.maventest.commandsystem.annotations.Label;

@Label(label = "list")
@Arguments(arguments = "")
public class CommandWhitelistList extends AbstractCommand {

    @Override
    public void execute(ICommandSender sender, ArgumentList argumentList) {

        Set<String> whiteList = MinecraftServer.getServer().getConfigurationManager().getWhiteListedIPs();
        sender.sendMessage(sender.translateString("commands.whitelist.list", whiteList.size(), MinecraftServer.getServer().getConfigurationManager().func_72373_m().length));
        sender.sendMessage(joinNiceString(whiteList.toArray()));

    }

    /**
     * Joins the given string array into a "x, y, and z" seperated string.
     */
    public String joinNiceString(Object[] par0ArrayOfObj) {
        StringBuilder var1 = new StringBuilder();

        for (int var2 = 0; var2 < par0ArrayOfObj.length; ++var2) {
            String var3 = par0ArrayOfObj[var2].toString();

            if (var2 > 0) {
                if (var2 == par0ArrayOfObj.length - 1) {
                    var1.append(" and ");
                } else {
                    var1.append(", ");
                }
            }

            var1.append(var3);
        }

        return var1.toString();
    }

}
