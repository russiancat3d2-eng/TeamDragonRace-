package com.example.teamdragonrace;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.*;

public class GameManager {
    private final TeamDragonRace plugin;
    public final Map<UUID, String> players = new HashMap<>(); // UUID to Team Name
    public final Set<UUID> deadPlayers = new HashSet<>();

    public GameManager(TeamDragonRace plugin) {
        this.plugin = plugin;
    }

    public void registerHeartRecipe() {
        ItemStack heart = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Revival Heart");
        meta.setEnchantmentGlintOverride(true);
        heart.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(plugin, "revival_heart");
        ShapedRecipe recipe = new ShapedRecipe(key, heart);
        recipe.shape("DDD", "DED", "DDD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('E', Material.EMERALD_BLOCK);
        Bukkit.addRecipe(recipe);
    }

    public void updateCompass(org.bukkit.entity.Player holder, org.bukkit.entity.Player target) {
        ItemStack compass = holder.getInventory().getItemInMainHand();
        if (compass.getType() != Material.COMPASS) return;

        CompassMeta meta = (CompassMeta) compass.getItemMeta();
        meta.setLodestoneTracked(false); // This allows it to work in the Nether
        meta.setLodestone(target.getLocation());
        compass.setItemMeta(meta);
        holder.sendActionBar(ChatColor.GOLD + "Tracking: " + ChatColor.WHITE + target.getName());
    }
}
