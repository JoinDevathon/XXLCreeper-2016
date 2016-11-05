package org.devathon.contest2016.Tuca;

import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

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

    public static void save(List<String> pages, String name, Player p){
        File f = new File(progs(p).getPath(), name);
        try {
            f.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(String page : pages){
                for(String line : page.replace("§0", "").split(Pattern.quote(" "))){
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
