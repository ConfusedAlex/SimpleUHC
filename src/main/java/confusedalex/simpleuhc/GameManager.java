package confusedalex.simpleuhc;

import confusedalex.simpleuhc.Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class GameManager {

    private World world;
    private Location worldCenter;
    private GameState gameState;
    private final SimpleUHC plugin;
    private final ArrayList<UUID> livingPlayer = new ArrayList<>();
    private final ArrayList<Location> spawnLocations = new ArrayList<>();
    private TeamManager teamManager = new TeamManager(this);
    AdminGUI adminGUI = new AdminGUI(this);

    public GameManager(SimpleUHC plugin) {
        this.plugin = plugin;
    }

    public boolean isProtectionTimeRunning() {
        return protectionTimeRunning;
    }

    public ArrayList<UUID> getLivingPlayer() {
        return livingPlayer;
    }

    public void setProtectionTimeRunning(boolean protectionTimeRunning) {
        this.protectionTimeRunning = protectionTimeRunning;
    }

    private boolean protectionTimeRunning;

    public ArrayList<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public void addSpawnLocation(Location location) {
        spawnLocations.add(location);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        switch (gameState) {
            case LOBBY:
                setProtectionTimeRunning(true);
                break;
            case STARTING:
                for (Player player : Bukkit.getOnlinePlayers()){
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.setOp(false);
                        player.getInventory().clear();
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setTotalExperience(0);
                        player.setLevel(0);
                        livingPlayer.add(player.getUniqueId());
                        Random random = new Random();
                        int ran = random.nextInt(spawnLocations.size());
                        player.teleport(spawnLocations.get(ran));
                        spawnLocations.remove(ran);
                    }
                }
                Countdown countdown = new Countdown(this, plugin);
                countdown.startCountdown();
                break;
            case ACTIVE:
                ProtectionTime protectionTime = new ProtectionTime(this, plugin);
                protectionTime.startPT();
                break;
            case PREDEATHMATCH:
                DeathMatchCountdown deathMatchCountdown = new DeathMatchCountdown(this, plugin);
                deathMatchCountdown.startDMC();
                break;
            case DEATHMATCH:
                Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 20)));
                break;
            case WON:
                for (Player player : Bukkit.getOnlinePlayers()){
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                    player.teleport(Bukkit.getPlayer(livingPlayer.get(0)).getLocation());
                    player.getInventory().clear();
                    player.setGameMode(GameMode.ADVENTURE);
                    player.getInventory().setItem(0, new ItemStack(Material.FIREWORK_ROCKET, 64));
                }
                break;
        }
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Location getWorldCenter() {
        return worldCenter;
    }

    public void setWorldCenter(Location worldCenter) {
        this.worldCenter = worldCenter;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
