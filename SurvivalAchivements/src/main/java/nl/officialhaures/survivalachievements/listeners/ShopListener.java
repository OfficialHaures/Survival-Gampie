package nl.officialhaures.survivalachievements.listeners;

import nl.officialhaures.survivalachievements.economy.EconomyManager;
import nl.officialhaures.survivalachievements.shop.ShopManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    private final ShopManager shopManager;
    private final EconomyManager economyManager;

    public ShopListener(ShopManager shopManager, EconomyManager economyManager) {
        this.shopManager = shopManager;
        this.economyManager = economyManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Shop")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null) {
                shopManager.purchaseItem(player, clickedItem);
            }
        }
    }
}
