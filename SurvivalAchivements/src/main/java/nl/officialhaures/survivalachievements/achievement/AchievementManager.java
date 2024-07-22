package nl.officialhaures.survivalachievements.achievement;

import nl.officialhaures.survivalachievements.SurvivalAchivements;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AchievementManager {

    private final SurvivalAchivements plugin;
    private final Map<UUID, Map<Achievement, Boolean>> playerAchievements;

    public AchievementManager(SurvivalAchivements plugin) {
        this.plugin = plugin;
        this.playerAchievements = new HashMap<>();
        loadAchievements();
    }

    private void loadAchievements() {
        FileConfiguration config = plugin.getConfig();
        for (String playerUUID : config.getConfigurationSection("achievements").getKeys(false)) {
            Map<Achievement, Boolean> achievements = new HashMap<>();
            for (Achievement achievement : Achievement.values()) {
                achievements.put(achievement, config.getBoolean("achievements." + playerUUID + "." + achievement.name()));
            }
            playerAchievements.put(UUID.fromString(playerUUID), achievements);
        }
    }

    public void saveAchievements() {
        FileConfiguration config = plugin.getConfig();
        for (Map.Entry<UUID, Map<Achievement, Boolean>> entry : playerAchievements.entrySet()) {
            UUID playerUUID = entry.getKey();
            Map<Achievement, Boolean> achievements = entry.getValue();
            for (Achievement achievement : Achievement.values()) {
                config.set("achievements." + playerUUID + "." + achievement.name(), achievements.get(achievement));
            }
        }
        plugin.saveConfig();
    }

    public boolean hasAchievement(UUID playerUUID, Achievement achievement) {
        return playerAchievements.getOrDefault(playerUUID, new HashMap<>()).getOrDefault(achievement, false);
    }

    public void grantAchievement(UUID playerUUID, Achievement achievement) {
        playerAchievements.computeIfAbsent(playerUUID, k -> new HashMap<>()).put(achievement, true);
        plugin.getEconomyManager().addMoney(playerUUID, achievement.getReward());
        saveAchievements();
    }

    public enum Achievement {
        WOOD_COLLECTOR(10),
        STONE_MINER(20),
        IRON_DIGGER(50);

        private final int reward;

        Achievement(int reward) {
            this.reward = reward;
        }

        public int getReward() {
            return reward;
        }
    }
}
