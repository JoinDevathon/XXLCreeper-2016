package org.devathon.contest2016.Tuca;

import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;

import java.io.File;

/**
 * Created by Simon on 05.11.2016.
 */
public class IOUtils {

    public static void checkDir(Player p){
        file(p).mkdirs();
        progs(p).mkdirs();
    }

    public static boolean getProgramExists(Player p, String prog){
        for(String str : progs(p).list()){
            if(str.equalsIgnoreCase(prog)){
                return true;
            }
        }
        return false;
    }

    public static String[] getProgramList(Player p){
        return progs(p).list();
    }

//-----------------------------------------------

    private static File file(Player p){
        return new File(DevathonPlugin.dataDir, p.getUniqueId().toString());
    }

    private static File progs(Player p){
        return new File(file(p).getPath(), "programs");
    }

    /*
        pid
        var
     */

}
