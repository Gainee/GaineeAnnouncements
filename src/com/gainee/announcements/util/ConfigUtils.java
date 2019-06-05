package com.gainee.announcements.util;

import org.bukkit.entity.*;

import com.gainee.announcements.*;

public class ConfigUtils
{
    public static ConfigUtils instance;
    
    public static ConfigUtils getInstance() {
        if (ConfigUtils.instance == null) {
            ConfigUtils.instance = new ConfigUtils();
        }
        return ConfigUtils.instance;
    }
    
    public void playSound(final Player p, final String path) {
        final float volume = (float)Main.getInstance().getConfig().getDouble(String.valueOf(path) + ".ses-kuvveti");
        final float pitch = (float)Main.getInstance().getConfig().getDouble(String.valueOf(path) + ".pitch");
        p.playSound(p.getLocation(), Sounds.valueOf(Main.getInstance().getConfig().getString(String.valueOf(path) + ".ses-ismi").toUpperCase()).bukkitSound(), volume, pitch);
    }
    
    public void playAnnouncementSound(final Player p, final String announcement) {
        final String str = "Anonslar." + announcement + ".Ses";
        if (!Main.getInstance().getConfig().getBoolean(String.valueOf(str) + ".durum")) {
            return;
        }
        this.playSound(p, str);
    }
    
    public int getSlot(final String path) {
        return Main.getInstance().getConfig().getInt(path);
    }
}
