package nl.officialhaures.survivalachievements.shop;

import nl.officialhaures.survivalachievements.SurvivalAchivements;
import nl.officialhaures.survivalachievements.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {

    private final SurvivalAchivements plugin;
    private final EconomyManager economyManager;
    private final Inventory shopInventory;

    public ShopManager(SurvivalAchivements plugin) {
        this.plugin = plugin;
        this.economyManager = plugin.getEconomyManager();
        this.shopInventory = Bukkit.createInventory(null, 9, "Shop");
        initializeShop();
    }

    private void initializeShop() {
        addItem(new ItemStack(Material.DIAMOND), 100, "Diamant");
        addItem(new ItemStack(Material.GOLDEN_APPLE), 50, "Gouden Appel");
        addItem(new ItemStack(Material.ENDER_PEARL, 5), 25, "Ender Parels");
    }

    private void addItem(ItemStack item, int price, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add("Prijs: " + price + " munten");
        meta.setLore(lore);
        item.setItemMeta(meta);
        shopInventory.addItem(item);
    }

    public void openShop(Player player) {
        player.openInventory(shopInventory);
    }

    public void purchaseItem(Player player, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasLore()) {
            List<String> lore = meta.getLore();
            if (lore != null && !lore.isEmpty()) {
                String priceString = lore.get(0).replace("Prijs: ", "").replace(" munten", "");
                int price = Integer.parseInt(priceString);
                if (economyManager.removeMoney(player.getUniqueId(), price)) {
                    player.getInventory().addItem(item);
                    player.sendMessage("Je hebt " + meta.getDisplayName() + " gekocht voor " + price + " munten.");
                } else {
                    player.sendMessage("Je hebt niet genoeg munten om dit item te kopen.");
                }
            }
        }
    }
}
