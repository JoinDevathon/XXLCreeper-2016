package org.devathon.contest2016;

import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.Vector3f;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftArmorStand;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.Tuca.Instructions;

import java.util.HashMap;

public class MiningTurtle {

    public EntityArmorStand chest;
    private EntityArmorStand body;
    private EntityArmorStand pick;
    private Face face;
    private HashMap<Material, Integer> minedItems;
    public boolean codeRunning = false;

    public MiningTurtle(Location loc, String name) {
        this.face = Face.SOUTH;
        this.minedItems = new HashMap<>();
        World world = ((CraftWorld)normalize(loc).getWorld()).getHandle();

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
        ((CraftArmorStand)pick.getBukkitEntity()).setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));

        setLocation(loc);
        chest.world.addEntity(chest);
        body.world.addEntity(body);
        pick.world.addEntity(pick);
        pick.setRightArmPose(new Vector3f(pick.rightArmPose.getX()-5, pick.rightArmPose.getY()-10, pick.rightArmPose.getZ()-15));
        rotate(Instructions.FORWARD);
    }

//-----------------------------------------------

    public void despawn(){
        chest.die();
        body.die();
        pick.die();
    }

//-----------------------------------------------

    private void setLocation(Location loc){
        loc = normalize(loc);
        Location locBody = moveLoc(loc, 0, -0.35, 0);
        Location locPick = moveLoc(loc, 0, 0.35, 0);

        chest.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        body.setLocation(locBody.getX(), locBody.getY(), locBody.getZ(), locBody.getYaw(), locBody.getPitch());
        pick.setLocation(locPick.getX(), locPick.getY(), locPick.getZ(), locPick.getYaw(), locPick.getPitch());
    }

    public void rotate(Instructions direction){
        if(direction == Instructions.BACKWARD){
            face = Face.getDirection(face, 180);
        } else if(direction == Instructions.LEFT){
            face = Face.getDirection(face, -90);
        } else if(direction == Instructions.RIGHT){
            face = Face.getDirection(face, 90);
        }

        //x -> forward
        //y -> rotate
        //z -> sideways
        chest.setHeadPose(new Vector3f(chest.headPose.getX(), face.getYaw(), chest.headPose.getZ()));
        //pick.setHeadPose(new Vector3f(pick.headPose.getX(), face.getYaw(), pick.headPose.getZ()));
        //pick.setLeftArmPose(new Vector3f(pick.leftArmPose.getX(), pick.leftArmPose.getY(), pick.leftArmPose.getZ()));
        Location loc = pick.getBukkitEntity().getLocation();
        pick.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), face.getYaw(), loc.getPitch());
    }

    public void move(Instructions direction){
        Location loc = chest.getBukkitEntity().getLocation();

        if(direction == Instructions.UP){
            setLocation(moveLoc(loc, 0, 1, 0));
        } else if(direction == Instructions.DOWN){
            setLocation(moveLoc(loc, 0, -1, 0));
        } else {
            setLocation(moveLoc(loc, face.x, 0, face.z));
            rotate(direction);
        }
    }

    public boolean isBlock(Instructions direction, int id){
        if(id == -1) return (getLocInDir(direction).getBlock().getType() == Material.AIR ? false : true);
        else return (getLocInDir(direction).getBlock().getTypeId() == id ? true : false);
    }

    public boolean isFull(){
        return minedItems.keySet().size() > 128;
    }

    public void mine(Instructions direction){
        rotate(direction);
        Location loc = moveLoc(chest.getBukkitEntity().getLocation(), face.x, 0, face.z);

        Material m = loc.getBlock().getType();
        loc.getBlock().setType(Material.AIR);

        minedItems.put(m, (minedItems.containsKey(m) ? minedItems.get(m) + 1 : 1));
    }

//-----------------------------------------------

    private Location getLocInDir(Instructions direction){
        if(direction == Instructions.FORWARD){
            //face
            return moveLoc(chest.getBukkitEntity().getLocation(), face.x, 0, face.z);
        } else if(direction == Instructions.BACKWARD){
            //negative face
            return moveLoc(chest.getBukkitEntity().getLocation(), -face.x, 0, -face.z);
        } else if(direction == Instructions.LEFT){
            //change values
            return moveLoc(chest.getBukkitEntity().getLocation(), face.z, 0, face.x);
        } else if(direction == Instructions.RIGHT){
            //change values and negotiate
            return moveLoc(chest.getBukkitEntity().getLocation(), -face.z, 0, -face.x);
        } else if(direction == Instructions.UP){
            return moveLoc(chest.getBukkitEntity().getLocation(), 0, 1, 0);
        } else if(direction == Instructions.DOWN){
            return moveLoc(chest.getBukkitEntity().getLocation(), 0, -1, 0);
        }
        return null;
    }

    private Location normalize(Location loc){ //x y z, !yaw!, pitch

        return new Location(loc.getWorld(),
                Math.floor(loc.getX()) + 0.5,
                loc.getY(),
                Math.floor(loc.getZ()) + 0.5,
                /*(loc.getYaw() > 315 ? 360 : (loc.getYaw() > 255 ? 270 : (loc.getYaw() >  135 ? 180 : (loc.getYaw() > 45 ? 90 : 0))))*/0, 0);
    }

    private Location moveLoc(Location loc, double x, double y, double z){
        return new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y, loc.getZ()+z);
    }

//-----------------------------------------------

    public enum Face{
        NORTH(180, 0, -1),
        EAST(270, 1, 0),
        SOUTH(0, 0, 1),
        WEST(90, -1, 0);

        private int yaw;
        public int x, z;
        Face(int yaw, int x, int z){
            this.yaw = yaw;
            this.x = x;
            this.z = z;
        }

        public int getYaw(){
            return this.yaw;
        }


        public static Face getDirection(Face current, int add) {
            int val = current.getYaw() + add;
            while (val > 360) val -= 360;
            while (val < 0) val += 360;

            for (Face direction : Face.values()) {
                if (direction.getYaw() == val) return direction;
            }
            return Face.NORTH;
        }
    }

}
