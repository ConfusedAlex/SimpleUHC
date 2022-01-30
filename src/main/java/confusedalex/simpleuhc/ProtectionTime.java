package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.TimeUnit;

public class ProtectionTime {
    int protectionTime = (SimpleUHC.instance.getConfig().getInt("ProtectionTime")*60);
    private final GameManager gameManager;
    private final SimpleUHC plugin;

    public ProtectionTime(GameManager gameManager, SimpleUHC plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    public void startPT(){
        final int fpt = protectionTime;
        gameManager.setProtectionTimeRunning(true);

        BossBar bossBar = Bukkit.createBossBar("ProtectionTime", BarColor.RED, BarStyle.SOLID);
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, task -> {
            if (protectionTime == 0){
                gameManager.setProtectionTimeRunning(false);
                bossBar.removeAll();
                task.cancel();
                return;
            }
            long minute = TimeUnit.SECONDS.toMinutes(protectionTime);
            long second = TimeUnit.SECONDS.toSeconds(protectionTime) - (TimeUnit.SECONDS.toMinutes(protectionTime) *60);
            if (second < 10){
                bossBar.setTitle(ChatColor.AQUA + "ProtectionTime: " + minute + ":0" + second);
            } else {
                bossBar.setTitle(ChatColor.AQUA + "ProtectionTime: " + minute + ":" + second);
            }
            bossBar.setProgress((double) protectionTime/fpt);
            protectionTime--;
        }, 20, 20);
    }
}