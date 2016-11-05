package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DevathonPlugin extends JavaPlugin {

    static final String CMD_TURTLE = "turtle";
    public static String dataDir;


    @Override
    public void onEnable() {
        getCommand(CMD_TURTLE).setExecutor(new CommandExecutor());
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        File data = new File(getDataFolder().getPath(), "Turtle");
        data.mkdirs();
        dataDir = data.getPath();
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }
}

