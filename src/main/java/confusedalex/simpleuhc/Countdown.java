package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Countdown {
    private int countdownTime = SimpleUHC.instance.getConfig().getInt("Countdown");
    private final GameManager gameManager;
    private final SimpleUHC plugin;

    public Countdown(GameManager gameManager, SimpleUHC plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    public void startCountdown(){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, task -> {
            if (countdownTime == 0){
                gameManager.setGameState(GameState.ACTIVE);
                task.cancel();
                return;
            }

            for (Player p : Bukkit.getOnlinePlayers()){
                if (countdownTime % 5 == 0 || countdownTime <= 10) {
                    p.sendTitle(String.valueOf(countdownTime), "", 0, 15, 5);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                }
            }
            countdownTime--;
        }, 20, 20);
    }
}