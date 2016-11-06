package org.devathon.contest2016;

import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftArmorStand;
import org.bukkit.inventory.ItemStack;

public class MiningTurtle_v2{

    public EntityArmorStand chest;
    private EntityArmorStand body;
    private EntityArmorStand pick;

    public MiningTurtle_v2(Location loc, String name) {
        final Location locx = normalize(loc);
        World world = ((CraftWorld)locx.getWorld()).getHandle();

        chest = new EntityArmorStand(world);
        chest.setBasePlate(false);
        chest.setInvisible(true);
        chest.setNoGravity(true);
        chest.setCustomNameVisible(true);
        chest.setCustomName(name);
        ((CraftArmorStand)chest.getBukkitEntity()).setAI(false);
        ((CraftArmorStand)chest.getBukkitEntity()).setHelmet(new ItemStack(Material.CHEST));

        body = new EntityArmorStand(world);
        body.setBasePlate(false);
        body.setInvisible(true);
        body.setNoGravity(true);
        ((CraftArmorStand)body.getBukkitEntity()).setAI(false);
        ((CraftArmorStand)body.getBukkitEntity()).setHelmet(new ItemStack(Material.IRON_BLOCK));

        pick = new EntityArmorStand(world);
        pick.setBasePlate(false);
        pick.setInvisible(true);
        pick.setNoGravity(true);
        ((CraftArmorStand)pick.getBukkitEntity()).setAI(false);
        ((CraftArmorStand)pick.getBukkitEntity()).setHelmet(new ItemStack(Material.DIAMOND_PICKAXE));

        setLocation(loc);
        chest.world.addEntity(chest);
        body.world.addEntity(body);
        pick.world.addEntity(pick);
    }

//-----------------------------------------------

    public void setLocation(Location loc){
        Location locBody = moveLoc(loc, 0, -0.35, 0, 0, 0);
        Location locPick = moveLoc(loc, 0.12, 0.30, -0.22, 0, 0);
        chest.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        body.setLocation(locBody.getX(), locBody.getY(), locBody.getZ(), locBody.getYaw(), locBody.getPitch());
        pick.setLocation(locPick.getX(), locPick.getY(), locPick.getZ(), locPick.getYaw(), locPick.getPitch());
    }

    public void move(){

    }

//-----------------------------------------------

    private Location normalize(Location loc){ //x y z, !yaw!, pitch
        if (loc.getYaw() < 0) loc.setYaw((loc.getYaw()+180)*-1);

        return new Location(loc.getWorld(),
                Math.floor(loc.getX()) + 0.5,
                loc.getY(),
                Math.floor(loc.getZ()) + 0.5,
                /*(loc.getYaw() > 315 ? 360 : (loc.getYaw() > 255 ? 270 : (loc.getYaw() >  135 ? 180 : (loc.getYaw() > 45 ? 90 : 0))))*/0, 0);
    }

    public Location moveLoc(Location loc, double x, double y, double z, float yaw, float pitch){
        return new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()+z, loc.getYaw()+yaw, loc.getPitch()+pitch);
    }

}
