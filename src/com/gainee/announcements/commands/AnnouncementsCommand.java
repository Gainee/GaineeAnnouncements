package com.gainee.announcements.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import com.gainee.announcements.*;
import com.gainee.announcements.announcements.*;
import com.gainee.announcements.util.*;

public class AnnouncementsCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("gaineeannouncements")) {
            return false;
        }
        if (!sender.hasPermission("gaineeannounce.admin")) {
            Message.sendMessage(sender, "YETKİN-YOK");
        }
        switch (args.length) {
            case 1: {
                if (args[0].equalsIgnoreCase("yardım")) {
                    return this.displayHelp(sender);
                }
                if (!args[0].equalsIgnoreCase("reload")) {
                    return this.invalidCommand(sender);
                }
                Main.getInstance().reloadPlugin();
                Message.sendMessage(sender, "CONFIG-YENİLENDİ");
                return true;
            }
            case 2: {
                final String lowerCase2;
                @SuppressWarnings("unused")
				final String lowerCase = lowerCase2 = args[0].toLowerCase();
                switch (lowerCase2) {
                    case "gönder": {
                        final String name = args[1];
                        if (Main.getInstance().getConfig().getConfigurationSection("Anonslar").getKeys(false).stream().noneMatch(name::equals)) {
                            Message.sendMessage(sender, "HATALI-KOMUT");
                            return false;
                        }
                        AnnouncementFramework.getInstance().announce(name);
                        return true;
                    }
                    case "göster": {
                        if (!(sender instanceof Player)) {
                            Message.sendMessage(sender, "CMD-GÖSTER");
                            return false;
                        }
                        final String announcementName = args[1];
                        if (Main.getInstance().getConfig().getConfigurationSection("Anonslar").getKeys(false).stream().noneMatch(announcementName::equals)) {
                            Message.sendMessage(sender, "HATALI-KOMUT");
                            return false;
                        }
                        AnnouncementFramework.getInstance().displayAnnouncement((Player)sender, announcementName);
                        return true;
                    }
                    default: {
                        return this.invalidCommand(sender);
                    }
                }
            }
            default: {
                return this.displayHelp(sender);
            }
        }
    }
    
    private boolean displayHelp(final CommandSender sender) {
        Main.getInstance().getConfig().getStringList("Ayarlar.Yardım").forEach(line -> sender.sendMessage(Util.color(line)));
        return true;
    }
    
    private boolean invalidCommand(final CommandSender sender) {
        Message.sendMessage(sender, "HATALI-CMD");
        return false;
    }
}
