package me.senyitko.main;

import com.neovisionaries.ws.client.WebSocket;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class PlayerDeathEvent implements Listener {
    public static HashMap<Player, String> deathsList = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent e) {
        final Main m =  Main.getPlugin(Main.class);
        Player p = e.getEntity();
        WebSocket ws = m.ws;
        String coords = "(" + p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ() + ")";
        ws.sendText("{\"type\":\"death\",\"player\":\"pName\",\"coords\":\"coordsName\"}".replace("pName", p.getDisplayName()).replace("coordsName", coords));
        if(deathsList.containsKey(p)) {
            deathsList.replace(p, coords);
        } else deathsList.put(p, coords);
    }

    // Public HashMaps
    public HashMap<Player, String> getDeathsList() {
        return deathsList;
    }
}
