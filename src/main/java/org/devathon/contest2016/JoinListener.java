package org.devathon.contest2016;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.devathon.contest2016.Tuca.IOUtils;

/**
 * Created by Simon on 05.11.2016.
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        IOUtils.checkDir(e.getPlayer());
    }

}
