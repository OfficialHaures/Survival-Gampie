package nl.officialhaures.survivalachievements;

import nl.officialhaures.survivalachievements.economy.EconomyManager;
import nl.officialhaures.survivalachievements.shop.ShopManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    private final ShopManager shopManager;
    private final EconomyManager economyManager;

    public ShopCommand(ShopManager shopManager, EconomyManager economyManager) {
        this.shopManager = shopManager;
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            shopManager.openShop(player);
            player.sendMessage("Je hebt " + economyManager.getMoney(player) + " munten.");
        }
        return true;
    }
}
