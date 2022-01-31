package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.commandmanager.CommandParser;

public final class SimpleUHC extends JavaPlugin {
    public static String prefix = ChatColor.LIGHT_PURPLE + "[SimpleUHC] " + ChatColor.GRAY;
    public static SimpleUHC instance;

    @Override
    public void onEnable() {
        instance = this;
        Config.loadConfig();
        GameManager gameManager = new GameManager(this);

        new CommandParser(this.getResource("commands.rdcml")).parse().register("simpleuhc", new Commands(gameManager));

//        this.getCommand("suhc").setExecutor(new Commands(gameManager));
        Bukkit.getPluginManager().registerEvents(new Events(gameManager, this), this);

        gameManager.setGameState(GameState.LOBBY);
        Bukkit.getConsoleSender().sendMessage(prefix + "Plugin loaded!");
    }

    @Override
    public void onDisable() {
    }
}