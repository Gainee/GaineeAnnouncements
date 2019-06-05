package com.gainee.announcements;

import org.bukkit.plugin.java.*;
import com.earth2me.essentials.*;
import com.gainee.announcements.announcements.*;
import com.gainee.announcements.commands.*;
import com.gainee.announcements.util.*;

import java.io.*;

import org.bukkit.command.*;
import org.bukkit.configuration.file.*;

import java.util.*;
import org.bukkit.scheduler.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.clip.placeholderapi.*;

public class Main extends JavaPlugin
{
    private Essentials ess;
    @SuppressWarnings("unused")
	private int announcementIndex;
    @SuppressWarnings("unused")
	private BukkitTask announcer;
    private Iterator<String> announcements;
    private File messages;
    private FileConfiguration messagesConfig;
    public static AnnouncementFramework announcementFramework;
    private static Boolean placeholdersEnabled;
    private long announcementPeriod;
    
    public Main() {
        this.announcementIndex = 1;
    }
    
    public void onEnable() {
        this.setup();
        this.announcements = this.getAnnouncements();
        this.announcementPeriod = getInstance().getConfig().getLong("Ayarlar.Zaman");
        Main.announcementFramework = new AnnouncementFramework();
        this.ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        final PluginManager pm = Bukkit.getPluginManager();
        Main.placeholdersEnabled = pm.isPluginEnabled("PlaceholderAPI");
        this.getCommand("gaineeannouncements").setExecutor((CommandExecutor)new AnnouncementsCommand());
        this.scheduleAnnouncements();
    }
    
    private void setup() {
        try {
            this.saveDefaultConfig();
            this.messages = new File(this.getDataFolder(), "mesajlar.yml");
            if (!this.messages.exists()) {
                this.messages.createNewFile();
                this.saveResource("mesajlar.yml", true);
            }
            this.messagesConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.messages);
        }
        catch (Exception ex) {}
    }
    
    public void onLoad() {
        EnchantGlow.getGlow();
    }
    
    public Essentials getEss() {
        return this.ess;
    }
    
    public FileConfiguration getMessages() {
        return this.messagesConfig;
    }
    
    private Iterator<String> getAnnouncements() {
        return new ArrayList<String>(getInstance().getConfig().getConfigurationSection("Anonslar").getKeys(false)).iterator();
    }
    
    private void scheduleAnnouncements() {
        this.announcer = new BukkitRunnable() {
            public void run() {
                Main.this.announcements = (Main.this.announcements.hasNext() ? Main.this.announcements : Main.this.getAnnouncements());
                if (!Main.this.announcements.hasNext()) {
                    return;
                }
                AnnouncementFramework.getInstance().announce(Main.this.announcements.next());
            }
        }.runTaskTimer((Plugin)getInstance(), 0L, this.announcementPeriod);
    }
    
    public void saveMessages() {
        try {
            this.messagesConfig.save(this.messages);
        }
        catch (Exception ex) {}
    }
    
    public void reloadMessages() {
        try {
            this.messagesConfig.load(this.messages);
            this.messagesConfig.save(this.messages);
        }
        catch (Exception ex) {}
    }
    
    public String getPrefix() {
        return String.valueOf(ChatColor.translateAlternateColorCodes('&', this.messagesConfig.getString("Prefix"))) + " ";
    }
    
    public String color(final Player p, final String input) {
        return Main.placeholdersEnabled ? ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, input)) : ChatColor.translateAlternateColorCodes('&', input);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Main getInstance() {
        return (Main)JavaPlugin.getPlugin((Class)Main.class);
    }
    
    public void reloadPlugin() {
        this.reloadConfig();
        this.reloadMessages();
        this.announcements = this.getAnnouncements();
    }
}
