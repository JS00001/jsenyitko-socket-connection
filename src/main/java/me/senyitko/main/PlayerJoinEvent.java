package me.senyitko.main;

import com.neovisionaries.ws.client.WebSocket;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void onBlockDispense(org.bukkit.event.player.PlayerJoinEvent e) {
        final Main m =  Main.getPlugin(Main.class);
        WebSocket ws = m.ws;
        ws.sendText("{\"type\":\"join\",\"player\":\"pName\"}".replace("pName", e.getPlayer().getDisplayName()));
    }
}
