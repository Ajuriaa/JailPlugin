package org.wasalona.jailplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Cells {
    public static Location getCell(int number) {
        World world = Bukkit.getWorld("world");
        System.out.println(Bukkit.getWorlds());
        switch (number) {
            case 1:
                return new Location(world, -438, 62, 484);
//            case 2:
//                return new Location(world, -434, 62, 484);
//            case 3:
//                return new Location(world, -429, 62, 484);
//            case 4:
//                return new Location(world, -423, 62, 484);
//            case 5:
//                return new Location(world, -418, 62, 484);
//            case 6:
//                return new Location(world, -419, 62, 472);
//            case 7:
//                return new Location(world, -423, 62, 472);
//            case 8:
//                return new Location(world, -429, 62, 472);
//            case 9:
//                return new Location(world, -434, 62, 472);
//            case 10:
//                return new Location(world, -438, 62, 472);
            default:
                return new Location(world, -438, 62, 484);
        }
    }
}
