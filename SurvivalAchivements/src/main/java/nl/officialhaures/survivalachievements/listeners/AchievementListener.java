package nl.officialhaures.survivalachievements.listeners;

import nl.officialhaures.survivalachievements.achievement.AchievementManager;
import nl.officialhaures.survivalachievements.economy.EconomyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class AchievementListener implements Listener {

    private final AchievementManager achievementManager;
    private final EconomyManager economyManager;

    public AchievementListener(AchievementManager achievementManager, EconomyManager economyManager) {
        this.achievementManager = achievementManager;
        this.economyManager = economyManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        if (blockType == Material.ACACIA_LOG) {
            checkAchievement(player, AchievementManager.Achievement.WOOD_COLLECTOR);
        } else if (blockType == Material.STONE) {
            checkAchievement(player, AchievementManager.Achievement.STONE_MINER);
        } else if (blockType == Material.IRON_ORE) {
            checkAchievement(player, AchievementManager.Achievement.IRON_DIGGER);
        }
    }

    private void checkAchievement(Player player, AchievementManager.Achievement achievement) {
        if (!achievementManager.hasAchievement(player.getUniqueId(), achievement)) {
            achievementManager.grantAchievement(player.getUniqueId(), achievement);
            player.sendMessage("Je hebt de achievement '" + achievement.name() + "' behaald! Je hebt " + achievement.getReward() + " munten ontvangen.");
        }
    }
}
