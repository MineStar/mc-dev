package net.minecraft.src;

import java.util.List;
import java.util.Map;

public interface ICommandManager {
    void handleCommand(ICommandSender var1, String var2);

    List getTabCompletionOptions(ICommandSender var1, String var2);

    List func_71557_a(ICommandSender var1);

    Map func_71555_a();
}
