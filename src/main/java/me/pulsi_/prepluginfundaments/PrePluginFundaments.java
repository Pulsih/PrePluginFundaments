package me.pulsi_.prepluginfundaments;

import org.bukkit.plugin.java.JavaPlugin;

public final class PrePluginFundaments extends JavaPlugin {

    public static PrePluginFundaments INSTANCE;
    private ConfigManager configManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        configManager = new ConfigManager(this);
        configManager.loadConfigs();

        dataManager = new DataManager(this);
        dataManager.setupPlugin();
    }

    @Override
    public void onDisable() {
        dataManager.shutdownMessage();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
