package de.minestar.commandsystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NumberInvalidException;
import net.minecraft.src.PlayerNotFoundException;

public class CommandUtils {

    /**
     * Returns true if the given substring is exactly equal to the start of the given string (case insensitive).
     */
    public static boolean doesStringStartWith(String par0Str, String par1Str) {
        return par1Str.regionMatches(true, 0, par0Str, 0, par0Str.length());
    }

    /**
     * Returns a List of strings (chosen from the given strings) which the last word in the given string array is a beginning-match for. (Tab completion).
     */
    public static List<String> getListOfStringsMatchingLastWord(String[] par0ArrayOfStr, String... par1ArrayOfStr) {
        String var2 = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList<String> var3 = new ArrayList<String>();
        String[] var4 = par1ArrayOfStr;
        int var5 = par1ArrayOfStr.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];

            if (doesStringStartWith(var2, var7)) {
                var3.add(var7);
            }
        }

        return var3;
    }

    /**
     * Returns a List of strings (chosen from the given string iterable) which the last word in the given string array is a beginning-match for. (Tab completion).
     */
    public static List<String> getListOfStringsFromIterableMatchingLastWord(String[] par0ArrayOfStr, Iterable<String> par1Iterable) {
        String var2 = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList<String> var3 = new ArrayList<String>();
        Iterator<String> var4 = par1Iterable.iterator();

        while (var4.hasNext()) {
            String var5 = (String) var4.next();

            if (doesStringStartWith(var2, var5)) {
                var3.add(var5);
            }
        }

        return var3;
    }

    /**
     * Parses an int from the given string.
     */
    public static int parseInt(ICommandSender sender, String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{text});
        }
    }

    /**
     * Parses an int from the given string within a specified bound.
     */
    public static int parseIntBounded(ICommandSender sender, String text, int minimum, int maximum) {
        int var = parseInt(sender, text);

        if (var < minimum) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{Integer.valueOf(var), Integer.valueOf(minimum)});
        } else if (var > maximum) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{Integer.valueOf(var), Integer.valueOf(maximum)});
        } else {
            return var;
        }
    }

    /**
     * Returns the given ICommandSender as a EntityPlayer or throw an exception.
     */
    public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            return (EntityPlayerMP) sender;
        } else {
            throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
        }
    }

    public static EntityPlayerMP getPlayerByName(String playerName) {
        EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerName);

        if (player == null) {
            throw new PlayerNotFoundException();
        } else {
            return player;
        }
    }

    /**
     * Parses an int from the given sring with a specified minimum.
     */
    public static int parseIntWithMin(ICommandSender sender, String text, int minimum) {
        return parseIntBounded(sender, text, minimum, Integer.MAX_VALUE);
    }
}
