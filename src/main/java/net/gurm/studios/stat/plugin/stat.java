package net.gurm.studios.stat.plugin;

import com.sun.jdi.LongValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class stat {

    public long[] readstat(String p) {
        File filen = new File("plugins/stat_plugin/player data" + p + ".txt");
        File folderl1 = new File("plugins/stat_plugin");
        File folderl2 = new File("plugins/stat_plugin/player data");
        long[] stat = new long[0];
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
            stat = new long[10];
            BufferedReader r = new BufferedReader(new FileReader(filen));
            List list = new ArrayList();
            String s;
            while (null != (s = r.readLine())) {
                list.add(Long.valueOf(cutter(s)));
            }
            r.close();
            for (int cnt = 0; cnt < 9; cnt++) {
                stat[cnt] = ((long) list.get(cnt));
            }
        } catch (Exception e) {
        }
        return stat;
    }
    public long cutter(String c){
        String[] cut = c.split(":");
        return Long.parseLong(cut[1]);
    }
}
