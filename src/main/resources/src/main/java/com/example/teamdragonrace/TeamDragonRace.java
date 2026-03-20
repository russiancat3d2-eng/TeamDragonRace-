package com.example.teamdragonrace;
import org.bukkit.plugin.java.JavaPlugin;

public class TeamDragonRace extends JavaPlugin {
    private GameManager gameManager;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);
        getCommand("race").setExecutor(new RaceCommand(gameManager));
        getServer().getPluginManager().registerEvents(new GameListener(gameManager), this);
        gameManager.registerHeartRecipe();
    }
}
