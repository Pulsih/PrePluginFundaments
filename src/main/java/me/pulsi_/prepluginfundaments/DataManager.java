package me.pulsi_.prepluginfundaments;

public class DataManager {
    
    private final PrePluginFundaments plugin;
    
    public DataManager(PrePluginFundaments plugin) {
        this.plugin = plugin;
    }

    public void startupMessage(long time) {
        Logger.log("");
        Logger.log("Logo");
        Logger.log("");
        long finalTime = System.currentTimeMillis() - time;
        Logger.log("... loaded! (" + finalTime + "ms)");
        Logger.log("");
    }

    public void shutdownMessage() {
        Logger.log("");
        Logger.log("Logo");
        Logger.log("");
    }

    public void setupPlugin() {
        long time = System.currentTimeMillis();
        setupCommands();
        setupEvents();
        reloadPlugin();
        startupMessage(time);
    }

    public void reloadPlugin() {
        ConfigManager cm = plugin.getConfigManager();
        cm.loadConfig(ConfigManager.Type.CONFIG);
        cm.loadConfig(ConfigManager.Type.MESSAGES);
    }

    private void setupCommands() {
        plugin.getCommand("").setExecutor(new MainCmd());
        plugin.getCommand("").setTabCompleter(new MainCmd());
    }

    private void setupEvents() {
        plugin.getServer().getPluginManager().registerEvents(new Listener(), plugin);
    }
}