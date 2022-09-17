package me.pulsi_.prepluginfundaments;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (args.length == 0) {

            return false;
        }

        switch (args[0]) {
            case "reload": {
                PrePluginFundaments.INSTANCE.getDataManager().reloadPlugin();
            }
            break;

            default:
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String label, String[] args) {

        switch (args.length) {
            case 1:
                List<String> listOfArgs = new ArrayList<>();
                if (s.hasPermission("")) listOfArgs.add("");

                List<String> args1 = new ArrayList<>();
                for (String a : listOfArgs) {
                    if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                        args1.add(a);
                }
                return args1;
        }
        return null;
    }
}
