package org.wasalona.jailplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ChestLocation {
    public static Location getChest(int number) {
        World world = Bukkit.getServer().getWorld("New World");

        switch (number) {
            case 1:
                return new Location(world, -416, 69, 472);
            case 2:
                return new Location(world, -416, 67, 472);
            case 3:
                return new Location(world, -416, 69, 475);
            case 4:
                return new Location(world, -416, 67, 475);
            case 5:
                return new Location(world, -416, 69, 478);
            case 6:
                return new Location(world, -416, 67, 478);
            case 7:
                return new Location(world, -416, 69, 481);
            case 8:
                return new Location(world, -416, 67, 481);
            default:
                return new Location(world, -416, 67, 484);
        }
    }
}
