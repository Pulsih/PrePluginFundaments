package me.pulsi_.prepluginfundaments;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String prefix = "";

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}