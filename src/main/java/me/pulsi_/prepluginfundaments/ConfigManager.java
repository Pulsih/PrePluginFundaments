package me.pulsi_.prepluginfundaments;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {

    private final Plugin plugin;
    private File configFile, messagesFile;
    private FileConfiguration config, messagesConfig;

    public enum Type {
        CONFIG,
        MESSAGES
    }

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!configFile.exists()) plugin.saveResource("config.yml", false);
        if (!messagesFile.exists()) plugin.saveResource("messages.yml", false);

        config = new YamlConfiguration();
        messagesConfig = new YamlConfiguration();

        updateConfig("config.yml");
        updateConfig("messages.yml");
    }

    public void saveConfig(Type type, boolean async) {
        switch (type) {
            case CONFIG:
                save(config, configFile, async);
                break;

            case MESSAGES:
                save(messagesConfig, messagesFile, async);
                break;
        }
    }

    private void save(FileConfiguration c, File f, boolean async) {
        if (!async) {
            save(c, f);
            return;
        }
        try {
            Bukkit.getScheduler().runTaskAsynchronously(PrePluginFundaments.INSTANCE, () -> {
                try {
                    c.save(f);
                } catch (Exception e) {
                    Bukkit.getScheduler().runTask(PrePluginFundaments.INSTANCE, () -> save(c, f));
                }
            });
        } catch (Exception e) {
            save(c, f);
        }
    }

    private void save(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    public void loadConfig(Type type) {
        switch (type) {
            case CONFIG:
                try {
                    config.load(configFile);
                } catch (IOException | InvalidConfigurationException e) {
                    Logger.error(e.getMessage());
                }
                break;

            case MESSAGES:
                try {
                    messagesConfig.load(messagesFile);
                } catch (IOException | InvalidConfigurationException e) {
                    Logger.error(e.getMessage());
                }
                break;
        }
    }

    public FileConfiguration getConfig(Type type) {
        switch (type) {
            case CONFIG:
                return config;
            case MESSAGES:
                return messagesConfig;
            default:
                return null;
        }
    }

    private void updateConfig(String config) {
        File serverFile = new File(plugin.getDataFolder(), config);
        YamlConfiguration serverConfig = YamlConfiguration.loadConfiguration(serverFile);

        InputStreamReader pluginStream = new InputStreamReader(PrePluginFundaments.INSTANCE.getResource(config), StandardCharsets.UTF_8);
        YamlConfiguration pluginConfig = YamlConfiguration.loadConfiguration(pluginStream);

        for (String path : pluginConfig.getKeys(true))
            serverConfig.set(path, serverConfig.get(path) == null ? pluginConfig.get(path) : serverConfig.get(path));

        save(serverConfig, serverFile, true);
    }
}