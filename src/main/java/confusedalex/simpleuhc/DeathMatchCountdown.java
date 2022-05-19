package confusedalex.simpleuhc;

import org.bukkit.boss.BarColor;

public class DeathMatchCountdown extends confusedalex.simpleuhc.BossBar {

    public DeathMatchCountdown(GameManager gameManager, SimpleUHC plugin) {
        super(gameManager, plugin);
    }

    @Override
    public void job(int seconds) {
        if (seconds == 0) {
            gameManager.setGameState(GameState.DEATHMATCH);
        }
    }

    public void startDMC(){
        int countdownTime = 300;
        createBossBar("Deathmatch begins in: ", countdownTime, BarColor.RED);
    }
}
