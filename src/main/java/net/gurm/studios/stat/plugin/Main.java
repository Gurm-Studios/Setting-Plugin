package net.gurm.studios.stat.plugin;

import net.gurm.studios.stat.plugin.commands.Start;
import net.gurm.studios.stat.plugin.commands.Stat;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;

public class Main extends JavaPlugin {

    public stat s =new stat();

    @Override
    public void onEnable(){
        getLogger().info("★Plugin [✔]");
        getCommand("스텟").setExecutor(new Stat());
        getCommand("시작").setExecutor(new Start());
    }



    @Override
    public void onDisable(){
        getLogger().info("★Plugin [❌]");
    }


    public void onJoin(PlayerJoinEvent e){
        s.readstat(String.valueOf(e));
    }

}
