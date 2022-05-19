package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Boss;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.TimeUnit;

public class ProtectionTime extends confusedalex.simpleuhc.BossBar {
    int protectionTime = (SimpleUHC.instance.getConfig().getInt("ProtectionTime")*60);

    public ProtectionTime(GameManager gameManager, SimpleUHC plugin) {
        super(gameManager, plugin);
    }

    @Override
    public void job(int seconds) {
        if (seconds == 0) {
            gameManager.setProtectionTimeRunning(false);
            gameManager.setGameState(GameState.ACTIVE);
        }
    }

    @Override
    public void start() {
        gameManager.setProtectionTimeRunning(true);
    }

    public void startPT(){
        createBossBar("Schutzzeit: ", protectionTime, BarColor.RED);
    }
}