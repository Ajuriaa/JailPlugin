package org.wasalona.jailplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Cells {
    public static Location getCell(int number) {
        World world = Bukkit.getWorld("New World");

        switch (number) {
            case 1:
                return new Location(world, -433, 62, 484);
            case 2:
                return new Location(world, -433, 62, 473);
            case 3:
                return new Location(world, -429, 62, 484);
            case 4:
                return new Location(world, -429, 62, 473);
            case 5:
                return new Location(world, -425, 62, 484);
            case 6:
                return new Location(world, -425, 62, 473);
            case 7:
                return new Location(world, -421, 62, 484);
            case 8:
                return new Location(world, -421, 62, 473);
            default:
                return new Location(world, -439, 62, 473);
        }
    }
}
