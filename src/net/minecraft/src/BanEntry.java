package net.minecraft.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BanEntry {
    public static final SimpleDateFormat field_73698_a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    /** Creates Ban Entry in the logger. */
    public static Logger loggerBanEntry = Logger.getLogger("Minecraft");
    private final String playerName;
    private Date created = new Date();
    private String source = "(Unknown)";
    private Date expireDate = null;
    private String reason = "Banned by an operator.";

    public BanEntry(String par1Str) {
        this.playerName = par1Str;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Date getCreated() {
        return this.created;
    }

    public void func_73681_a(Date par1Date) {
        this.created = par1Date != null ? par1Date : new Date();
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String par1Str) {
        this.source = par1Str;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date par1Date) {
        this.expireDate = par1Date;
    }

    public boolean hasExpired() {
        return this.expireDate == null ? false : this.expireDate.before(new Date());
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String par1Str) {
        this.reason = par1Str;
    }

    public String func_73685_g() {
        StringBuilder var1 = new StringBuilder();
        var1.append(this.getPlayerName());
        var1.append("|");
        var1.append(field_73698_a.format(this.getCreated()));
        var1.append("|");
        var1.append(this.getSource());
        var1.append("|");
        var1.append(this.getExpireDate() == null ? "Forever" : field_73698_a.format(this.getExpireDate()));
        var1.append("|");
        var1.append(this.getReason());
        return var1.toString();
    }

    public static BanEntry func_73688_c(String par0Str) {
        if (par0Str.trim().length() < 2) {
            return null;
        } else {
            String[] var1 = par0Str.trim().split(Pattern.quote("|"), 5);
            BanEntry var2 = new BanEntry(var1[0].trim());
            byte var3 = 0;
            int var10000 = var1.length;
            int var7 = var3 + 1;

            if (var10000 <= var7) {
                return var2;
            } else {
                try {
                    var2.func_73681_a(field_73698_a.parse(var1[var7].trim()));
                } catch (ParseException var6) {
                    loggerBanEntry.log(Level.WARNING, "Could not read creation date format for ban entry \'" + var2.getPlayerName() + "\' (was: \'" + var1[var7] + "\')", var6);
                }

                var10000 = var1.length;
                ++var7;

                if (var10000 <= var7) {
                    return var2;
                } else {
                    var2.setSource(var1[var7].trim());
                    var10000 = var1.length;
                    ++var7;

                    if (var10000 <= var7) {
                        return var2;
                    } else {
                        try {
                            String var4 = var1[var7].trim();

                            if (!var4.equalsIgnoreCase("Forever") && var4.length() > 0) {
                                var2.setExpireDate(field_73698_a.parse(var4));
                            }
                        } catch (ParseException var5) {
                            loggerBanEntry.log(Level.WARNING, "Could not read expiry date format for ban entry \'" + var2.getPlayerName() + "\' (was: \'" + var1[var7] + "\')", var5);
                        }

                        var10000 = var1.length;
                        ++var7;

                        if (var10000 <= var7) {
                            return var2;
                        } else {
                            var2.setReason(var1[var7].trim());
                            return var2;
                        }
                    }
                }
            }
        }
    }
}
