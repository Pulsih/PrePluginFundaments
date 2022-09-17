package me.pulsi_.prepluginfundaments;

import org.bukkit.Bukkit;

public class Logger {

    public static void log(String log) {
        Bukkit.getConsoleSender().sendMessage(ChatUtils.color(log));
    }

    public static void log(String... logs) {
        for (String log : logs) Bukkit.getConsoleSender().sendMessage(ChatUtils.color(log));
    }

    public static void error(String error) {
        log(ChatUtils.prefix + " &8[&cERROR&8] &c" + error);
    }

    public static void warn(String warn) {
        log(ChatUtils.prefix + " &8[&aWARN&8] &a" + warn);
    }

    public static void info(String info) {
        log(ChatUtils.prefix + " &8[&9INFO&8] &9" + info);
    }
}