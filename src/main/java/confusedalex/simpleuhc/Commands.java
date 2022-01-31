package confusedalex.simpleuhc;

import confusedalex.simpleuhc.Teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import redempt.redlib.commandmanager.CommandHook;

public class Commands /* implements CommandExecutor */  {
    private final String prefix = SimpleUHC.prefix;
    private final GameManager gameManager;
    TeamManager tm;

    public Commands(GameManager gameManager) {
        this.gameManager = gameManager;
        tm = gameManager.getTeamManager();

    }

    @CommandHook("start")
    public void start(CommandSender sender) {
        if (gameManager.getSpawnLocations().size() == 0) {
            sender.sendMessage(SimpleUHC.prefix + "No SpawnPoints set! \n" +
                    SimpleUHC.prefix + "Set SpawnPoints with /addspawn");
        } else if (gameManager.getSpawnLocations().size() > 0) {
            sender.sendMessage(SimpleUHC.prefix + gameManager.getSpawnLocations().size() + " SpawnPoints set! \n"
                    + SimpleUHC.prefix + "You need to have a SpawnPoint for each Player! \n"
                    + SimpleUHC.prefix + "To start the game type " + ChatColor.AQUA + "/start confirm");
        }
    }

    @CommandHook("startconfirm")
    public void startConfirm(CommandSender sender){
        gameManager.setGameState(GameState.STARTING);
    }

    @CommandHook("addspawn")
    public void addSpawn(Player player){
        if (gameManager.getSpawnLocations().contains(player.getLocation())){
            player.sendMessage(prefix + "You already added this spawn!");
        } else {
            gameManager.addSpawnLocation(player.getLocation());
            player.sendMessage(prefix + "Spawn Point added! " + "(" + gameManager.getSpawnLocations().size() + ")");
        }
    }

    @CommandHook("setup")
    public void setup(Player player){
        gameManager.adminGUI.openGUI(player);
    }

    @CommandHook("teamcreate")
    public void teamCreate(Player player, String name){
        if (gameManager.getTeamManager().getUnavailableNames().contains(name)) {
            player.sendMessage(prefix + "This team name was already taken!");
        } else if (gameManager.getTeamManager().getTeam(player) == null) {
            gameManager.getTeamManager().newTeam(name, player);
            player.sendMessage(prefix + "Team " + name + " was created!");
            player.sendMessage(prefix + "You are now the leader of team " + name);
        } else {
            player.sendMessage(prefix + "You are already in a Team!");
        }
    }

    @CommandHook("teamleave")
    public void leaveTeam(Player player){
        if (tm.isInATeam(player)) {
            tm.getTeam(player).removeMember(player);
            player.sendMessage(prefix + "You left your team!");
        }

        if (tm.getTeam(player).getSize() == 0){
            gameManager.getTeamManager().deleteTeam(tm.getTeam(player));
        }
    }

    @CommandHook("teamjoin")
    public void teamJoin(Player player, String name){
        if (!tm.isInATeam(player)){
           if (gameManager.getTeamManager().getTeamByName(name) != null) {
               gameManager.getTeamManager().getTeamByName(name).addMember(player);
               player.sendMessage(prefix + "You are now member of team " + name);
           } else {
               player.sendMessage(prefix + "There is no team named " + name);
               player.sendMessage(prefix + "Check for proper capitalization!");
           }
        } else {
            player.sendMessage(prefix + "You are already in a Team!");
        }
    }

    @CommandHook("setcenter")
    public void setCenter(Player player){

    }
}
