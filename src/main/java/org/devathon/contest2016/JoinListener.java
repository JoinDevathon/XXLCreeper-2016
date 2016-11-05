package org.devathon.contest2016;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.devathon.contest2016.Tuca.IOUtils;

/**
 * Created by Simon on 05.11.2016.
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        IOUtils.checkDir(e.getPlayer());
    }

    @EventHandler
    public void onBookEdit(PlayerEditBookEvent e){
        BookMeta meta = e.getNewBookMeta();

        for(String str : meta.getPages()){
            e.getPlayer().sendMessage(str.replace("\n", "#").replace("§", "&"));
        }
    }

}
