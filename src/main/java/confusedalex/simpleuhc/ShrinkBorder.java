package confusedalex.simpleuhc;

import org.bukkit.*;
import org.bukkit.boss.BarColor;

public class ShrinkBorder extends BossBar{

    WorldBorder border;

    public ShrinkBorder(GameManager gameManager, SimpleUHC plugin) {
        super(gameManager, plugin);
        border = this.gameManager.getWorldBorder();
    }

    public void shrink(int blocks, int seconds){
        border.setWarningTime(5);
        border.setSize((border.getSize() - blocks), seconds);

        createBossBar("Border shrinking!", seconds, BarColor.YELLOW);
    }

    @Override
    public void job(int seconds) {
        if (seconds == 0){
            Bukkit.getConsoleSender().sendMessage("Ich bin ein Hurensohn");
            gameManager.setGameState(GameState.PREDEATHMATCH);
        }
        Bukkit.getConsoleSender().sendMessage("yeet");
    }
}
