package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.devathon.contest2016.Tuca.Instructions;

/**
 * Created by Simon on 06.11.2016.
 */
public class MiningTurtle {

    private String name;
    private ArmorStand chest;
    private ArmorStand body;
    private ArmorStand pick;
    private Direction face;

    /*
        TODO add furnace at back for fuel
     */

    public MiningTurtle(String name){
        this.name = name;
    }

//-----------------------------------------------

    public void spawn(Location loc){
        //System.out.println(loc.toString());
        face = Direction.NORTH;
        loc = normalize(loc);
        //System.out.println(loc.toString());
        chest = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        chest.setCustomNameVisible(true);
        chest.setVisible(false);
        chest.setGravity(false);
        chest.setBasePlate(false);
        chest.setArms(false);
        chest.setAI(false);
        chest.setCustomName(name);
        chest.setHelmet(new ItemStack(Material.TRAPPED_CHEST));

        body = (ArmorStand) loc.getWorld().spawnEntity(moveLoc(loc, 0, -0.35, 0, 0, 0), EntityType.ARMOR_STAND);
        body.setCustomNameVisible(false);
        body.setVisible(false);
        body.setGravity(false);
        body.setBasePlate(false);
        body.setArms(false);
        body.setAI(false);
        body.setHelmet(new ItemStack(Material.IRON_BLOCK));

        pick = (ArmorStand) loc.getWorld().spawnEntity(moveLoc(loc, 0.12, 0.30, -0.22, 0, 0), EntityType.ARMOR_STAND);
        pick.setCustomNameVisible(false);
        pick.setVisible(false);
        pick.setGravity(false);
        pick.setBasePlate(false);
        pick.setArms(false);
        pick.setAI(false);
        pick.setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));


        move(Instructions.UP);
        move(Instructions.LEFT);
        move(Instructions.LEFT);
        move(Instructions.DOWN);
    }

//-----------------------------------------------

    public void turn(Instructions direction){
        chest.setHeadPose(new EulerAngle(0, 75, 0));
        /*if(direction == Instructions.LEFT){
            chest.teleport(changeDirection(chest.getLocation(), (face = Direction.getDirection(face, 90))));
        } else if(direction == Instructions.RIGHT){
            chest.teleport(changeDirection(chest.getLocation(), (face = Direction.getDirection(face, -90))));
        } else if(direction == Instructions.BACKWARD){
            chest.teleport(changeDirection(chest.getLocation(), (face = Direction.getDirection(face, 180))));
        }*/
    }

    public void move(Instructions direction){
        if(direction == Instructions.FORWARD){
            chest.teleport(moveLoc(chest.getLocation(), face.x, 0, face.z, 0, 0));
            body.teleport(moveLoc(body.getLocation(), face.x, 0, face.z, 0, 0));
            pick.teleport(moveLoc(pick.getLocation(), face.x, 0, face.z, 0, 0));
        } else if(direction == Instructions.BACKWARD){
            chest.teleport(moveLoc(chest.getLocation(), face.x, 0, face.z, 0, 0));
            body.teleport(moveLoc(body.getLocation(), face.x, 0, face.z, 0, 0));
            pick.teleport(moveLoc(pick.getLocation(), face.x, 0, face.z, 0, 0));
        } else if(direction == Instructions.LEFT){
            chest.teleport(moveLoc(chest.getLocation(), face.x, 0, face.z, 0, 0));
            body.teleport(moveLoc(body.getLocation(), face.x, 0, face.z, 0, 0));
            pick.teleport(moveLoc(pick.getLocation(), face.x, 0, face.z, 0, 0));
        } else if(direction == Instructions.RIGHT){
            chest.teleport(moveLoc(chest.getLocation(), face.x, 0, face.z, 0, 0));
            body.teleport(moveLoc(body.getLocation(), face.x, 0, face.z, 0, 0));
            pick.teleport(moveLoc(pick.getLocation(), face.x, 0, face.z, 0, 0));
        } else if(direction == Instructions.UP){
            chest.teleport(moveLoc(chest.getLocation(), 0, 1, 0, 0, 0));
            body.teleport(moveLoc(body.getLocation(), 0, 1, 0, 0, 0));
            pick.teleport(moveLoc(pick.getLocation(), 0, 1, 0, 0, 0));
        } else if(direction == Instructions.DOWN){
            chest.teleport(moveLoc(chest.getLocation(), 0, -1, 0, 0, 0));
            body.teleport(moveLoc(body.getLocation(), 0, -1, 0, 0, 0));
            pick.teleport(moveLoc(pick.getLocation(), 0, -1, 0, 0, 0));
        }

        ((CraftArmorStand)chest).getHandle().m();
    }

//-----------------------------------------------

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

    private Location changeDirection(Location loc, Direction dir){
        return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), dir.getYaw(), loc.getPitch());
    }

//-----------------------------------------------

    public enum Direction{
        NORTH(0, 0, -1),
        EAST(90, 1, 0),
        SOUTH(180, 0, 1),
        WEST(270, -1, 0);

        private int yaw;
        int x, z;
        Direction(int yaw, int x, int z){
            this.yaw = yaw;
            this.x = x;
            this.z = z;
        }

        public int getYaw(){
            return this.yaw;
        }

        public static Direction getDirection(Direction current, int add){
            int val = current.getYaw() + add;
            while(val > 360) val -= 360;
            while(val < 0) val += 360;

            for(Direction direction : Direction.values()){
                if(direction.getYaw() == val) return direction;
            }
            return Direction.NORTH;
        }
    }

}
