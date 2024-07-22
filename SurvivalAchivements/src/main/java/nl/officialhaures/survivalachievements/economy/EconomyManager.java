package nl.officialhaures.survivalachievements.economy;

import nl.officialhaures.survivalachievements.SurvivalAchivements;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {

    private final SurvivalAchivements plugin;
    private final Map<UUID, Integer> playerMoney;

    public EconomyManager(SurvivalAchivements plugin) {
        this.plugin = plugin;
        this.playerMoney = new HashMap<>();
        loadMoney();
    }

    private void loadMoney() {
        FileConfiguration config = plugin.getConfig();
        for (String uuidString : config.getConfigurationSection("money").getKeys(false)) {
            UUID playerUUID = UUID.fromString(uuidString);
            int money = config.getInt("money." + uuidString);
            playerMoney.put(playerUUID, money);
        }
    }

    public void saveMoney() {
        FileConfiguration config = plugin.getConfig();
        for (Map.Entry<UUID, Integer> entry : playerMoney.entrySet()) {
            UUID playerUUID = entry.getKey();
            int money = entry.getValue();
            config.set("money." + playerUUID.toString(), money);
        }
        plugin.saveConfig();
    }

    public int getMoney(Player player) {
        return playerMoney.getOrDefault(player.getUniqueId(), 0);
    }

    public void addMoney(UUID playerUUID, int amount) {
        playerMoney.put(playerUUID, playerMoney.getOrDefault(playerUUID, 0) + amount);
        saveMoney();
    }

    public boolean removeMoney(UUID playerUUID, int amount) {
        int currentMoney = playerMoney.getOrDefault(playerUUID, 0);
        if (currentMoney >= amount) {
            playerMoney.put(playerUUID, currentMoney - amount);
            saveMoney();
            return true;
        }
        return false;
    }
}
