package org.devathon.contest2016;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.devathon.contest2016.Json.TellRaw;
import org.devathon.contest2016.Tuca.IOUtils;

import java.util.Timer;
import java.util.TimerTask;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        IOUtils.checkDir(e.getPlayer());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                e.getPlayer().sendMessage("§6Welcome to §5TUCA§6, your new MiningTurtle programming language!");
                e.getPlayer().sendMessage("§7The language is not completely ready yet so some features will be missing.");
                e.getPlayer().sendMessage("§7If you need Help, you can hover and click most messages to make things easier for you.");
                new TellRaw("§7Feel free to try our §3sample§7 by typing §8/turtle sample §7or by clicking ")
                        .addClickAndHover("§8*here*", "§7Yes here are often usefull informations", "/turtle sample", TellRaw.ClickEvent.RunCommand).send(e.getPlayer());
                e.getPlayer().sendMessage("");
                e.getPlayer().performCommand("turtle");
            }
        }, 5000);
    }

    @EventHandler
    public void onDC(PlayerQuitEvent e){
        if(CommandExecutor.player_turtle.containsKey(e.getPlayer().getUniqueId())){
            if(CommandExecutor.player_turtle.get(e.getPlayer().getUniqueId()) != null){
                CommandExecutor.player_turtle.get(e.getPlayer().getUniqueId()).despawn();
            }
        }
    }

}
