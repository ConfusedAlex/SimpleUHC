package confusedalex.simpleuhc.Teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
    String name;
    Player leader;
    ChatColor color;
    ArrayList<UUID> members = new ArrayList<>();

    public Team(String name, Player leader, ChatColor color) {
        this.name = name;
        this.leader = leader;
        this.color = color;
        addMember(leader);
    }

    public ChatColor getColor() {
        return color;
    }

    public void addMember(Player player){
        members.add(player.getUniqueId());
        player.setPlayerListName(color + "[" + name + "] " + player.getName() + ChatColor.WHITE);
        player.setDisplayName(color + "[" + name + "] " + player.getName() + ChatColor.WHITE);
    }

    public void removeMember(Player player) {
        members.remove(player.getUniqueId());
    }

    public String getName() {
        return name;
    }

    public int getSize(){
        return members.size();
    }

    public boolean isMember(Player player){
        return members.contains(player.getUniqueId());
    }
}
