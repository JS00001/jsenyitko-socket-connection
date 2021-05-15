package me.senyitko.main;

import com.neovisionaries.ws.client.WebSocket;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {
    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent e) {
        final Main m =  Main.getPlugin(Main.class);
        Player p = e.getEntity();
        String coords = "(" + p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ() + ")";
        WebSocket ws = m.ws;
        ws.sendText("{\"type\":\"death\",\"player\":\"pName\",\"coords\":\"coordsName\"}".replace("pName", p.getDisplayName()).replace("coordsName", coords));
    }
}
