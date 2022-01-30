package confusedalex.simpleuhc;

import confusedalex.simpleuhc.Teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private final String prefix = SimpleUHC.prefix;
    private final GameManager gameManager;

    public Commands(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player sp = (Player) sender;
        Team st = gameManager.getTeamManager().getTeam(sp);
        if (command.getName().equalsIgnoreCase("suhc") || command.getName().equalsIgnoreCase("simpleuhc")) {
            if (args.length == 0) {
                sender.sendMessage(SimpleUHC.prefix + ChatColor.GRAY + "Use " + ChatColor.AQUA + "/suhc" + ChatColor.GRAY + " help for a list of commands.");
            } else {
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.GOLD + "SimpleUHC Commands");
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.AQUA + "/suhc " + ChatColor.GRAY + "- The main command");
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.AQUA + "/suhc help " + ChatColor.GRAY + "- Shows this page");
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.AQUA + "/suhc start " + ChatColor.GRAY + "- Starts the game");
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.AQUA + "/suhc addspawn " + ChatColor.GRAY + "- Adds spawns");
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.AQUA + "/suhc team create <NAME> " + ChatColor.GRAY + "- Creates a team");
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.AQUA + "/suhc team leave " + ChatColor.GRAY + "- Makes you leave your current team");
                    sender.sendMessage(SimpleUHC.prefix + ChatColor.AQUA + "/suhc team join <TEAMNAME> " + ChatColor.GRAY + "- Joins the selected team");
                }
                if (args[0].equalsIgnoreCase("start")) {
                    if (gameManager.getSpawnLocations().size() == 0) {
                        sender.sendMessage(SimpleUHC.prefix + "No SpawnPoints set! \n" +
                                SimpleUHC.prefix + "Set SpawnPoints with /addspawn");
                    } else if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
                        gameManager.setGameState(GameState.STARTING);
                    } else if (gameManager.getSpawnLocations().size() > 0) {
                        sender.sendMessage(SimpleUHC.prefix + gameManager.getSpawnLocations().size() + " SpawnPoints set! \n"
                                + SimpleUHC.prefix + "You need to have a SpawnPoint for each Player! \n"
                                + SimpleUHC.prefix + "To start the game type " + ChatColor.AQUA + "/start confirm");
                    }
                }
                if (args[0].equalsIgnoreCase("addspawn")) {
                    Player player = (Player) sender;
                    gameManager.addSpawnLocation(player.getLocation());
                    sender.sendMessage(prefix + "Spawn Point added! " + "(" + gameManager.getSpawnLocations().size() + ")");
                }
                if (args[0].equalsIgnoreCase("setup")) {
                    gameManager.adminGUI.openGUI(sp);
                }
                if (args[0].equalsIgnoreCase("team")) {
                    if (args.length >= 2 ) {
                        if (args[1].equalsIgnoreCase("create")) {
                            if (args.length != 3) {
                                sender.sendMessage(prefix + ChatColor.AQUA + "/suhc team create <NAME>");
                            } else if (gameManager.getTeamManager().getUnavailableNames().contains(args[2])) {
                              sender.sendMessage(prefix + "This team name was already taken!");
                            } else if (st == null) {
                                gameManager.getTeamManager().newTeam(args[2], sp);
                                sender.sendMessage(prefix + "Team " + args[2] + " was created!");
                                sender.sendMessage(prefix + "You are now the leader of team " + args[2]);
                            } else {
                                sender.sendMessage(prefix + "You are already in a Team!");
                            }
                        } else if (args[1].equalsIgnoreCase("leave")){
                            st.removeMember(sp);
                            sender.sendMessage(prefix + "You left your team!");
                            if (st.getSize() == 0){
                                gameManager.getTeamManager().deleteTeam(st);
                            }
                        } else if (args[1].equalsIgnoreCase("join")){
                                if (args.length != 3){
                                    sender.sendMessage(prefix + ChatColor.AQUA + "/suhc team join <TEAMNAME>");
                                } else if (st == null){
                                   if (gameManager.getTeamManager().getTeamByName(args[2]) != null) {
                                       gameManager.getTeamManager().getTeamByName(args[2]).addMember(sp);
                                       sender.sendMessage(prefix + "You are now member of team " + args[2]);
                                   } else {
                                       sender.sendMessage(prefix + "There is no team named " + args[2]);
                                       sender.sendMessage(prefix + "Check for proper capitalization");
                                   }
                                } else {
                                    sender.sendMessage(prefix + "You are already in a Team!");
                                }
                        }
                    }
                }
            }
        }
        return true;
    }
}
