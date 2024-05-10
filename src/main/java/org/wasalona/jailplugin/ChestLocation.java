package org.wasalona.jailplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ChestLocation {
    public static Location getChest(int number) {
        World world = Bukkit.getServer().getWorld("World");

        switch (number) {
            case 1:
                return new Location(world, -416, 69, 472);
//            case 2:
//                return  new Location(world, 0, 0, 0);
//            case 3:
//                return "-429 62 484";
//            case 4:
//                return "-423 62 484";
//            case 5:
//                return "-418 62 484";
//            case 6:
//                return "-419 62 472";
//            case 7:
//                return "-423 62 472";
//            case 8:
//                return "-429 62 472";
//            case 9:
//                return "-434 62 472";
//            case 10:
//                return "-438 62 472";
            default:
                return new Location(world, -416, 69, 472);
        }
    }
}
