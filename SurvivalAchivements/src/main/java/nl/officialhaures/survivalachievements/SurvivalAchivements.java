package nl.officialhaures.survivalachievements;

import nl.officialhaures.survivalachievements.achievement.AchievementManager;
import nl.officialhaures.survivalachievements.economy.EconomyManager;
import nl.officialhaures.survivalachievements.listeners.AchievementListener;
import nl.officialhaures.survivalachievements.listeners.ShopListener;
import nl.officialhaures.survivalachievements.shop.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalAchivements extends JavaPlugin {

    private AchievementManager achievementManager;
    private EconomyManager economyManager;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        // Initialiseer managers
        achievementManager = new AchievementManager(this);
        economyManager = new EconomyManager(this);
        shopManager = new ShopManager(this);

        // Registreer event listeners
        getServer().getPluginManager().registerEvents(new AchievementListener(achievementManager, economyManager), this);
        getServer().getPluginManager().registerEvents(new ShopListener(shopManager, economyManager), this);

        // Registreer commands
        getCommand("shop").setExecutor(new ShopCommand(shopManager, economyManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public AchievementManager getAchievementManager() {
        return achievementManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }
}
