package me.senyitko.main;
import com.neovisionaries.ws.client.WebSocket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Commands implements CommandExecutor {
    final Main m =  Main.getPlugin(Main.class);
    final PlayerDeathEvent deathEvent = new PlayerDeathEvent();
    HashMap<Player, String> deathsList = deathEvent.getDeathsList();
    WebSocket ws = m.ws;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        // Ping Command
        if (command.getName().equalsIgnoreCase("ping")) {
            if (args.length == 1) {

                String playerCoords = "(" + ((Player) sender).getLocation().getBlockX() + "," + ((Player) sender).getLocation().getBlockY() + "," + ((Player) sender).getLocation().getBlockZ() + ")";
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BOLD + "" + ChatColor.RED + sender.getName() + "'s Coords" + ChatColor.GRAY + "] " + ChatColor.WHITE + args[0] + ": " +  playerCoords);
                ws.sendText("{\"type\":\"ping\",\"player\":\"pName\", \"coords\":\"coordsList\"}".replace("pName", p.getName()).replace("coordsList", args[0] + ": " + playerCoords));
                p.sendMessage(Format.f("&dYour ping was successfully sent to the webserver. "));

            } else p.sendMessage(Format.f("&cSpecify a descriptor for this location. Ex: /ping stronghold"));
        }

        // Death Command
        else if(command.getName().equalsIgnoreCase("death")) {
            if(deathsList.containsKey(p)) {
                p.sendMessage(Format.f("&dYour last death was at: " + deathsList.get(p)));
            } else p.sendMessage(Format.f("&cYou have no last death stored."));
        }

        // Help Command
        else if(command.getName().equalsIgnoreCase("help")) {
            p.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------");
            p.sendMessage(ChatColor.LIGHT_PURPLE  + "" + ChatColor.BOLD + "jsenyitko.tech");
            p.sendMessage(ChatColor.LIGHT_PURPLE + " * /help" + ChatColor.GRAY + " - Shows the help command");
            p.sendMessage(ChatColor.LIGHT_PURPLE + " * /ping <location name>" + ChatColor.GRAY + " - Saves coordinates in chat and online");
            p.sendMessage(ChatColor.LIGHT_PURPLE + " * /death" + ChatColor.GRAY + " - Shows your last death location");
            p.sendMessage(ChatColor.LIGHT_PURPLE + " * /name <name>" + ChatColor.GRAY + " - Renames a weapon");
            p.sendMessage(ChatColor.LIGHT_PURPLE + " * /tp <ign>" + ChatColor.GRAY + " - Teleports you to a player");
            p.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------------------");
        }

        // Name Command
        else if(command.getName().equalsIgnoreCase("name")) {
            ItemStack item = p.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if(item.getType() != Material.AIR) {
                if(args.length >= 1) {

                    meta.setDisplayName(Format.f(String.join(" ", args)));
                    item.setItemMeta(meta);
                    p.sendMessage(Format.f("&dSuccessfully renamed item to: " + String.join(" ", args)));

                } else p.sendMessage(Format.f("&cSpecify a name."));
            } else p.sendMessage(Format.f("&cYou must be holding an item."));
        }

        // Teleport Command
        else if(command.getName().equalsIgnoreCase("tp")) {
            if(args.length == 1) {
                Player teleportTo = Bukkit.getPlayer(args[0]);
                if (teleportTo != null) {

                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "teleport " + p.getName() + " " + teleportTo.getName());
                    ws.sendText("{\"type\":\"tp\",\"player\":\"pName\", \"target\":\"targetName\"}".replace("pName", p.getName()).replace("targetName", teleportTo.getName()));
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "You teleported to " + teleportTo.getName());
                    teleportTo.sendMessage(ChatColor.LIGHT_PURPLE + p.getName() + " teleported to you");

                } else p.sendMessage(Format.f("&cThis user is not online."));
            } else p.sendMessage(Format.f("&cSpecify a user to teleport to."));
        }
        return false;
    }
}

