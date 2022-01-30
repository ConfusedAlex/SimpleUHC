package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.TimeUnit;

public class DeathMatchCountdown {
    private int countdownTime = (60);
    private final GameManager gameManager;
    private final SimpleUHC plugin;

    public DeathMatchCountdown(GameManager gameManager, SimpleUHC plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    public void startDMC(){
        final int fpt = countdownTime;

        BossBar bossBar = Bukkit.createBossBar("Deathmatch", BarColor.RED, BarStyle.SOLID);
        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, task -> {
            if (countdownTime == 0){
                gameManager.setGameState(GameState.DEATHMATCH);
                bossBar.removeAll();
                task.cancel();
                return;
            }
            long minute = TimeUnit.SECONDS.toMinutes(countdownTime);
            long second = TimeUnit.SECONDS.toSeconds(countdownTime) - (TimeUnit.SECONDS.toMinutes(countdownTime) *60);
            if (second < 10){
                bossBar.setTitle(ChatColor.AQUA + "Deathmatch begins in: " + minute + ":0" + second);
            } else {
                bossBar.setTitle(ChatColor.AQUA + "Deathmatch begins in: " + minute + ":" + second);
            }
            bossBar.setProgress((double) countdownTime/fpt);
            countdownTime--;
        }, 20, 20);
    }
}
