package org.devathon.contest2016.Tuca;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.devathon.contest2016.DevathonPlugin;

import java.io.*;
import java.util.ArrayList;
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
                for(String line : page.replace("§0", "").split(Pattern.quote("\n"))){
                    writer.write(line.trim());
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String name, Player p){
        File f = new File(progs(p), name);
        if(f.exists()) f.delete();
    }

    private static final int book_lines = 14;
    public static ItemStack read(String name, Player p){
        File f = new File(progs(p), name);
        ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
        BookMeta meta = (BookMeta) stack.getItemMeta();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line, temp = "";
            int lineCount = 0;
            ArrayList<String> lines = new ArrayList<>();
            while((line = reader.readLine()) != null) {
                if (lineCount < book_lines) {
                    temp += line + "\n";
                    lineCount++;
                } else {
                    lines.add(temp.substring(0, temp.length() - 1));
                    temp = "";
                    lineCount = 0;
                }
            }
            lines.add(temp.substring(0, temp.length() - 1));
            reader.close();

            meta.setPages(lines);
            stack.setItemMeta(meta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stack;
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
