package com.example.teamdragonrace;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GameListener implements Listener {
    private final GameManager manager;

    public GameListener(GameManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.setGameMode(GameMode.SPECTATOR);
        manager.deadPlayers.add(player.getUniqueId());
    }

    @EventHandler
    public void onFriendlyFire(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof Player victim) {
            String teamA = manager.players.get(attacker.getUniqueId());
            String teamB = manager.players.get(victim.getUniqueId());
            if (teamA != null && teamA.equals(teamB)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRevive(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().getType() == Material.TOTEM_OF_UNDYING) {
            if (event.getItem().getItemMeta().getDisplayName().contains("Revival Heart")) {
                // Logic to find a dead teammate and revive them
                String myTeam = manager.players.get(player.getUniqueId());
                for (java.util.UUID uuid : manager.deadPlayers) {
                    if (manager.players.get(uuid).equals(myTeam)) {
                        Player deadTeammate = Bukkit.getPlayer(uuid);
                        if (deadTeammate != null) {
                            deadTeammate.setGameMode(GameMode.SURVIVAL);
                            deadTeammate.teleport(player.getLocation());
                            manager.deadPlayers.remove(uuid);
                            event.getItem().setAmount(event.getItem().getAmount() - 1);
                            Bukkit.broadcastMessage(ChatColor.GREEN + deadTeammate.getName() + " was revived!");
                            return;
                        }
                    }
                }
                player.sendMessage(ChatColor.RED + "No teammates to revive!");
                event.setCancelled(true);
            }
        }
    }
}
