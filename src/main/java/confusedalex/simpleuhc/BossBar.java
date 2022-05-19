package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.TimeUnit;

public class BossBar {

    final GameManager gameManager;
    final SimpleUHC plugin;
    int time;

    public BossBar(GameManager gameManager, SimpleUHC plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    public void createBossBar(String title, int seconds, BarColor barColor){
        time = seconds;
        final int ft = time;

        org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(ChatColor.AQUA + title, barColor, BarStyle.SOLID);
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        start();

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, task -> {
            job(time);

            if (time == 0){
                bossBar.removeAll();
                task.cancel();
                return;
            }

            long minute = TimeUnit.SECONDS.toMinutes(time);
            long second = TimeUnit.SECONDS.toSeconds(time) - (TimeUnit.SECONDS.toMinutes(time) *60);
            if (second < 10){
                bossBar.setTitle(ChatColor.AQUA + title + minute + ":0" + second);
            } else {
                bossBar.setTitle(ChatColor.AQUA + title + minute + ":" + second);
            }
            bossBar.setProgress((double) time/ft);
            time--;
        }, 20, 20);
    }

    public void job(int seconds) {}
    public void start(){}
}