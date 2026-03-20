package com.example.teamdragonrace;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RaceCommand implements CommandExecutor {
    private final GameManager manager;

    public RaceCommand(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length > 1 && args[0].equalsIgnoreCase("join")) {
            manager.players.put(player.getUniqueId(), args[1]);
            player.sendMessage("Joined team: " + args[1]);
        }
        return true;
    }
}
