package confusedalex.simpleuhc;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AdminGUI {
    private GameManager gameManager;
    Gui gui;

    public AdminGUI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void createGUI(){
        gui = Gui.gui()
                .title(Component.text("SimpleUHC"))
                .rows(3)
                .create();

        GuiItem addSpawnItem = ItemBuilder.from(Material.STONE).setName("Set SpawnPoint").lore(Component.text("SpawnPoints: " + gameManager.getSpawnLocations().size())).asGuiItem(event -> {
            gameManager.addSpawnLocation(event.getWhoClicked().getLocation());
            event.getWhoClicked().sendMessage("Hi");
            gui.update();
        });

        gui.disableItemTake();
        gui.disableItemSwap();
        gui.disableItemDrop();
        gui.disableItemPlace();
        gui.addItem(addSpawnItem);
        gui.getFiller().fill(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).asGuiItem());
    }

    public void openGUI(Player player){
        createGUI();
        gui.open(player);
    }






}
