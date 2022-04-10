package net.gurm.studios.stat.plugin.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.util.UUID;

public class Start implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        UUID p = null;
        if (args.length == 0) {
            commandSender.sendMessage("player를 입력하세요");
        } else {
            p = Bukkit.getPlayer(args[0]).getUniqueId();
        }
        File filen = new File("plugins/stat_plugin/player data" + p + ".txt");
        File folderl1 = new File("plugins/stat_plugin");
        File folderl2 = new File("plugins/stat_plugin/player data");

        try {
            if (!filen.exists()) {
                folderl1.mkdir();
                folderl2.mkdir();
                filen.createNewFile();

            }
            BufferedWriter w = new BufferedWriter(new FileWriter(filen));
            w.append("level:0\r\n" +
                    "xp:0\r\n" +
                    "max_xp:100\r\n" +
                    "stat_point:5\r\n" +
                    "limit_point:1\r\n" +
                    "power:0\r\n" +
                    "agility:0\n" +
                    "tenacious:0\n" +
                    "vitality:0\n" +
                    "mana:0\n" +
                    "mana_power:0\n");
            w.flush();
            w.close();
        } catch (Exception e) {
        }

        return false;
    }
}
