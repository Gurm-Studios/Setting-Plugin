package net.gurm.minecraft.server.plugin.chat;


import net.gurm.minecraft.server.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ChatBuffer {
    private Main plugin;
    private int maxBubbleHeight;
    private int maxBubbleWidth;
    private int bubblesInterval;
    private Map<String, Queue<String>> chatQueue = new HashMap<String, Queue<String>>();


    public ChatBuffer(Main main)
    {
        maxBubbleHeight = (3);
        maxBubbleWidth = (15);
        bubblesInterval = (5);
        this.plugin = main;
    }

    public void receiveChat(Player player, String msg)
    {

        if (msg.length() <= maxBubbleWidth)
        {
            queueChat(player, msg+"\n");
            return;
        }

        msg = msg+" ";
        String chat = "";
        int delimPos;
        int lineCount = 0;

        while (msg.length() > 0)
        {
            delimPos = msg.lastIndexOf(' ', maxBubbleWidth);
            if (delimPos < 0) delimPos = msg.indexOf(' ', maxBubbleWidth);
            if (delimPos < 0) delimPos = msg.length();

            chat += msg.substring(0, delimPos);
            msg = msg.substring(delimPos+1);

            ++lineCount;
            if (lineCount % maxBubbleHeight == 0 || msg.length() == 0)
            {
                queueChat(player, chat+(msg.length() == 0 ? "\n" : "...\n"));
                chat = "";
            }
            else
                chat += "\n";
        }
    }

    private void queueChat(Player player, String chat)
    {
        // if no player buffer yet, create it and schedule this message
        String playerId = ""+player.getUniqueId();
        if (!chatQueue.containsKey(playerId))
        {
            chatQueue.put(playerId, new LinkedList<String>());
            scheduleMessageUpdate(player, playerId, 0);
        }

        // queue this message
        chatQueue.get(playerId).add(chat);
    }

    // ... and this method will take previously queued chat messages by one for display
    private void scheduleMessageUpdate(Player player, String playerId, int timer)
    {
        //BukkitTask chatTimer = new BukkitRunnable()
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // player could be not chatting or offline, check him and collect his garbage
                if (chatQueue.get(playerId).size() < 1 || !player.isOnline())
                {
                    chatQueue.remove(playerId);
                    return;
                }

                // pull queued message, send it to be displayed and get the duration, schedule the next message
                String chat = chatQueue.get(playerId).poll();
                int bubbleDuration = plugin.bubbles.receiveMessage(player, playerId, chat);
                scheduleMessageUpdate(player, playerId, bubbleDuration+bubblesInterval);
            }
        }.runTaskLater(plugin, timer);
    }
}
