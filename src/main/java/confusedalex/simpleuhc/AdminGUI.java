package confusedalex.simpleuhc;

import confusedalex.simpleuhc.Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;

import static confusedalex.simpleuhc.SimpleUHC.prefix;

public class AdminGUI {
    private GameManager gameManager;
    InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "SimpleUHC - Setup"));


    public AdminGUI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void createGUI(){
        ItemButton addSpawnPoint = ItemButton.create(new ItemBuilder(Material.EMERALD_BLOCK)
                .setName("Add SpawnPoint").addLore("SpawnPoints: " + gameManager.getSpawnLocations().size()), e -> {
            if (gameManager.getSpawnLocations().contains(e.getWhoClicked().getLocation())){
                e.getWhoClicked().sendMessage(prefix + "You already added this spawn!");
            } else {
                gameManager.addSpawnLocation(e.getWhoClicked().getLocation());
            }
            createGUI();
        });

        ItemButton startGame = ItemButton.create(new ItemBuilder(Material.DIAMOND)
                .setName("Start Game")
                .addLore("SpawnPoints: " + gameManager.getSpawnLocations().size())
                .addLore("Online Players: " + Bukkit.getOnlinePlayers().size())
                .addLore("Teams: " + gameManager.getTeamManager().getTeams().size()), e -> {
                    gameManager.setGameState(GameState.STARTING);
                    e.getWhoClicked().closeInventory();
                });

        gui.fill(0,9, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        gui.addButton(addSpawnPoint, 5);
        gui.addButton(startGame, 8);
    }

    public void openGUI(Player player){
        createGUI();
        gui.open(player);
    }







}
