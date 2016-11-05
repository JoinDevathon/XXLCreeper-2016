package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {

    static final String CMD_TURTLE = "turtle";


    @Override
    public void onEnable() {
        getCommand(CMD_TURTLE).setExecutor(new CommandExecutor());
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }
}

