package confusedalex.simpleuhc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;

import java.awt.*;

import static confusedalex.simpleuhc.SimpleUHC.prefix;

public class AdminGUI {
    private GameManager gameManager;
    InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "SimpleUHC - Setup"));


    public AdminGUI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void createGUI(){
        ItemButton button = ItemButton.create(new ItemBuilder(Material.EMERALD_BLOCK)
                .setName("Click me!").addLore("SpawnPoints: " + gameManager.getSpawnLocations().size()), e -> {
            gameManager.addSpawnLocation(e.getWhoClicked().getLocation());
            createGUI();
        });
        gui.addButton(button, 5);
//        gui.fill(0,9, Material.GLASS);
    }

    public void openGUI(Player player){
        createGUI();
        gui.open(player);
    }







}
