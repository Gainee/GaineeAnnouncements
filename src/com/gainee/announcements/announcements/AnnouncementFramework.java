package com.gainee.announcements.announcements;

import org.bukkit.*;
import org.bukkit.plugin.*;

import com.gainee.announcements.*;
import com.gainee.announcements.util.*;

import org.bukkit.entity.*;

public class AnnouncementFramework
{
    public static AnnouncementFramework getInstance() {
        return Main.announcementFramework;
    }
    
    public void announce(final String announcement) {
        Bukkit.getScheduler().runTask((Plugin)Main.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(p -> this.displayAnnouncement(p, announcement)));
    }
    
    public void displayAnnouncement(final Player player, final String announcement) {
        final Boolean centered = this.isAnnouncementCentered(announcement);
        Main.getInstance().getConfig().getStringList("Anonslar." + announcement + ".satırlar").stream().map(line -> Main.getInstance().color(player, line)).forEachOrdered(line -> {
            if (centered) {
                ChatCenter.sendCenteredMessage(player, line);
            }
            else {
                player.sendMessage(line);
            }
            return;
        });
        ConfigUtils.getInstance().playAnnouncementSound(player, announcement);
    }
    
    public Integer getAnnouncementOrder(final Player player, final String announcement) {
        return Main.getInstance().getConfig().getInt("Anonslar." + announcement + ".adet");
    }
    
    public boolean isAnnouncementCentered(final String announcement) {
        return Main.getInstance().getConfig().getBoolean("Anonslar." + announcement + ".ortalama");
    }
}
