package org.wasalona.jailplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

            // Handle freeing from jail
            if (args[0].equalsIgnoreCase("free")) {
                return freeFromJail(args, player);
            }

            // Handle main command
            if (args.length != 2) {
                player.sendMessage("Usage: /jail <playername> <cellNumber>");
                return false;
            }

            // Check permissions
            if (!player.hasPermission("jail.jail")) {
                player.sendMessage("You don't have permission to use this command.");
                return false;
            }

            return putInJail(args, player);
        }
        return false;
    }

    private void executeCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    private boolean putInJail(String[] args, Player player) {
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
        ItemStack[] extraItems = target.getInventory().getExtraContents();
        Block block = chestLocation.getBlock();

        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            Inventory chestInventory = chest.getSnapshotInventory();

            // Add each ItemStack to the chest inventory
            for (ItemStack item : items) {
                if(item != null && item.getType() != Material.AIR){
                    chestInventory.addItem(item);
                    target.getInventory().removeItem(item);
                }
            }
            for(ItemStack item : extraItems){
                if(item != null && item.getType() != Material.AIR){
                    chestInventory.addItem(item);
                }
            }

            target.getInventory().setArmorContents(null);
            target.getInventory().clear();
            executeCommand("curios clear " + target.getName());
            executeCommand("lp user " + target.getName() + " permission set home.tp false");
            executeCommand("lp user " + target.getName() + " meta setprefix 100 \"&6&l[&c&lPRISIONERO&6&l] &f\"");

            // Update the chest state to reflect the changes
            chest.update(true, true);
        } else {
            player.sendMessage(ChatColor.RED + "Block at " + chestLocation + " is not a chest!");
        }


        Bukkit.broadcastMessage("" + ChatColor.RED + ChatColor.BOLD +"Player " + target.getName() + " has been jailed in cell " + cellNumber);
        return true;
    }

    private boolean freeFromJail(String[] args, Player player) {
        // Check permissions
        if (!player.hasPermission("jail.free")) {
            player.sendMessage("You don't have permission to use this command.");
            return false;
        }

        if(args.length != 3){
            player.sendMessage("Usage: /jail free <playername> <cellNumber>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);
        int cellNumber = Integer.parseInt(args[2]);

        if (target == null || !target.isOnline()) {
            player.sendMessage("Player not found or is not online.");
            return false;
        }

        Location chestLocation = ChestLocation.getChest(cellNumber);
        Block block = chestLocation.getBlock();

        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            Inventory chestInventory = chest.getSnapshotInventory();

            ItemStack[] items = chestInventory.getContents();

            for (ItemStack item : items) {
                if(item != null && item.getType() != Material.AIR){
                    target.getInventory().addItem(item);
                    chestInventory.remove(item);
                }
            }
            executeCommand("lp user " + target.getName() + " permission unset home.tp");
            executeCommand("lp user " + target.getName() + " meta removeprefix 100");

            // Update the chest state to reflect the changes
            chest.update(true, true);
        } else {
            player.sendMessage(ChatColor.RED + "Block at " + chestLocation + " is not a chest!");
        }

        target.teleport(new Location(Bukkit.getWorld("New World"), -445, 63, 494));
        target.sendMessage(ChatColor.GREEN + "You have been freed from jail! Be good.");
        return true;
    }
}
