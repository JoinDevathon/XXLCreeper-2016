package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Simon on 06.11.2016.
 */
public class MiningTurtle {

    private String name;
    private ArmorStand chest;
    private ArmorStand body;
    private ArmorStand pick;

    /*
        TODO add furnace at back for fuel
     */

    public MiningTurtle(String name){
        this.name = name;
    }

//-----------------------------------------------

    public void spawn(Location loc){
        //System.out.println(loc.toString());

        loc = normalize(loc);
        //System.out.println(loc.toString());
        chest = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        chest.setCustomNameVisible(true);
        chest.setVisible(false);
        chest.setGravity(false);
        chest.setBasePlate(false);
        chest.setArms(false);
        chest.setCustomName(name);
        chest.setHelmet(new ItemStack(Material.TRAPPED_CHEST));

        body = (ArmorStand) loc.getWorld().spawnEntity(moveLoc(loc, 0, -0.35, 0, 0, 0), EntityType.ARMOR_STAND);
        body.setCustomNameVisible(false);
        body.setVisible(false);
        body.setGravity(false);
        body.setBasePlate(false);
        body.setArms(false);
        body.setHelmet(new ItemStack(Material.IRON_BLOCK));

        pick = (ArmorStand) loc.getWorld().spawnEntity(moveLoc(loc, 0.12, 0.30, -0.22, 0, 0), EntityType.ARMOR_STAND);
        pick.setCustomNameVisible(false);
        pick.setVisible(false);
        pick.setGravity(false);
        pick.setBasePlate(false);
        pick.setArms(false);
        pick.setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
    }


    private Location moveLoc(Location loc, double x, double y, double z, float yaw, float pitch){
        return new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()+z, loc.getYaw()+yaw, loc.getPitch()+pitch);
    }

    private Location normalize(Location loc){ //x y z, !yaw!, pitch
        if (loc.getYaw() < 0) loc.setYaw((loc.getYaw()+180)*-1);

        return new Location(loc.getWorld(),
                Math.floor(loc.getX()) + 0.5,
                loc.getY(),
                Math.floor(loc.getZ()) + 0.5,
                /*(loc.getYaw() > 315 ? 360 : (loc.getYaw() > 255 ? 270 : (loc.getYaw() >  135 ? 180 : (loc.getYaw() > 45 ? 90 : 0))))*/0, 0);
    }
}
