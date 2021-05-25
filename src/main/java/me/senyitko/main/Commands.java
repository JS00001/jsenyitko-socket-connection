package me.senyitko.main;
import com.neovisionaries.ws.client.WebSocket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    final Main m =  Main.getPlugin(Main.class);
    WebSocket ws = m.ws;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        // Ping Command
        if (command.getName().equalsIgnoreCase("ping")) {
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BOLD + "" + ChatColor.RED + sender.getName() + "'s Coords" + ChatColor.GRAY + "] " + ChatColor.WHITE + ((Player) sender).getLocation().getBlockX() + "," + ((Player) sender).getLocation().getBlockY() + "," + ((Player) sender).getLocation().getBlockZ());
        }

        else if(command.getName().equalsIgnoreCase("event")) {

        }

        else if(command.getName().equalsIgnoreCase("tp")) {
            if(args.length == 1) {
                Player teleportTo = Bukkit.getPlayer(args[0]);
                if (teleportTo != null) {

                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "teleport " + p.getName() + " " + teleportTo.getName());
                    ws.sendText("{\"type\":\"tp\",\"player\":\"pName\", \"target\":\"targetName\"}".replace("pName", p.getName()).replace("targetName", teleportTo.getName()));
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "You teleported to " + teleportTo.getName());
                    teleportTo.sendMessage(ChatColor.LIGHT_PURPLE + p.getName() + " teleported to you");

                } else p.sendMessage(ChatColor.RED + "This user is not online.");
            } else p.sendMessage(ChatColor.RED + "Specify a user to teleport to.");
        }
        return false;
    }
}

