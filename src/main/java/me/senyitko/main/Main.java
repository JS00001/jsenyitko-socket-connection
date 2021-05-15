package me.senyitko.main;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;

public final class Main extends JavaPlugin {
    public WebSocket ws;
    {
        try {
            ws = new WebSocketFactory().createSocket("ws://69.55.61.83:8080", 3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList < String > messages = new ArrayList < String > ();

    @Override
    public void onEnable() {
        System.out.println("Plugin Loaded!");
        try {
            this.registerEvents();
            this.connect();
            this.timer();
        } catch (IOException | WebSocketException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws IOException, WebSocketException {
        ws.connect();
        ws.addListener(new WebSocketAdapter() {
            public void onTextMessage(WebSocket websocket, String message) {
                messages.add(message);
            }
        });
    }

    private void registerEvents() {
        PluginManager p = this.getServer().getPluginManager();
        p.registerEvents(new PlayerJoinEvent(), this);
        p.registerEvents(new PlayerDeathEvent(), this);
    }

    private void timer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < messages.size(); i++) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), messages.get(i));
                }
                messages.clear();
            }
        }, 0, 60);
    }
}