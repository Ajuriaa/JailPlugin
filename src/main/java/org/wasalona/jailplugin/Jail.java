package org.wasalona.jailplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Jail extends JavaPlugin implements CommandExecutor, Listener {

    @Override
    public void onEnable() {
        getLogger().info("JailPlugin has been enabled!");
        // Register command executor for /bounty command
        Objects.requireNonNull(getCommand("jail")).setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("JailPlugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("jail")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to use this command!");
                return false;
            }

            Player player = (Player) sender;

            // Check permissions
            if (!player.hasPermission("jail.jail")) {
                player.sendMessage("You don't have permission to use this command.");
                return false;
            }

            // Handle main bounty command
            if (args.length != 2) {
                player.sendMessage("Usage: /jail <playername> <cellNumber>");
                return false;
            }


            Player target = Bukkit.getPlayer(args[0]);
            int cellNumber = Integer.parseInt(args[1]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("Player not found or is not online.");
                return false;
            }

            Location cell = Cells.getCell(cellNumber);
            Location chestLocation = ChestLocation.getChest(cellNumber);
            target.teleport(cell);

            ItemStack[] items = target.getInventory().getContents();
            Block block = chestLocation.getBlock();
            Chest chest = (Chest) block.getState();
            chest.getInventory().setContents(items);
            target.getInventory().clear();

            Bukkit.broadcastMessage("" + ChatColor.RED + ChatColor.BOLD +"Player " + target.getName() + " has been jailed in cell " + cellNumber);

            return true;
        }
        return false;
    }
}
