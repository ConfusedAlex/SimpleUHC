package confusedalex.simpleuhc.Teams;

import confusedalex.simpleuhc.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class TeamManager {
    private final GameManager gameManager;
    ArrayList<Team> teams = new ArrayList<>();
    ArrayList<ChatColor> availableColors = new ArrayList<>();
    ArrayList<String> unavailableNames = new ArrayList<>();

    public ArrayList<String> getUnavailableNames() {
        return unavailableNames;
    }

    public TeamManager(GameManager gameManager) {
        this.gameManager = gameManager;
        availableColors.add(ChatColor.BLUE);
        availableColors.add(ChatColor.GREEN);
        availableColors.add(ChatColor.DARK_GREEN);
        availableColors.add(ChatColor.RED);
        availableColors.add(ChatColor.DARK_RED);
        availableColors.add(ChatColor.DARK_PURPLE);
        availableColors.add(ChatColor.YELLOW);
        availableColors.add(ChatColor.AQUA);
        availableColors.add(ChatColor.GOLD);
        availableColors.add(ChatColor.DARK_AQUA);
    }

    public void newTeam(String name, Player leader){
        Random random = new Random();
        int ran = random.nextInt(availableColors.size());
        Team team = new Team(name, leader, availableColors.get(ran));
        availableColors.remove(ran);
        unavailableNames.add(name);
        teams.add(team);
    }

    public int livingTeams(){
        int teams = 0;
        for (UUID uuid : gameManager.getLivingPlayer()){
            if (isInTeam(Bukkit.getPlayer(uuid))){
                teams++;
            }
        }
        return teams;
    }

    public Team getTeam(Player player){
        for (Team t : teams){
            if (t.isMember(player)){
                return t;
            }
        }
        return null;
    }

    public boolean isInTeam(Player player){
        for (Team t : teams){
            if (t.isMember(player)){
                return true;
            }
        }
        return false;
    }

    public Team getTeamByName(String name){
        for (Team t : teams){
            if (t.getName().equals(name)){
                return t;
            }
        }
        return null;
    }

    public void deleteTeam(Team t){
        availableColors.add(t.getColor());
        unavailableNames.remove(t.getName());
        teams.remove(t);
    }
}
