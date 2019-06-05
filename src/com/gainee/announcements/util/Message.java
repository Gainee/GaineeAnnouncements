package com.gainee.announcements.util;

import org.bukkit.command.*;

import com.gainee.announcements.*;

import org.bukkit.*;

public class Message
{
    public static void sendMessage(final CommandSender p, final MessagePart str) {
        if (str.getPart().isEmpty()) {
            return;
        }
        p.sendMessage(String.valueOf(Main.getInstance().getPrefix()) + str.getPart());
    }
    
    public static void sendMessage(final CommandSender p, final String str) {
        final MessagePart strPart = generate(str);
        if (strPart.getPart().isEmpty()) {
            return;
        }
        p.sendMessage(String.valueOf(Main.getInstance().getPrefix()) + strPart.getPart());
    }
    
    public static MessagePart generate(final String str) {
        return new MessagePart(str);
    }
    
    public static class MessagePart
    {
        public String str;
        
        public MessagePart(final String str) {
            this.str = (Main.getInstance().getMessages().isString(str.toUpperCase()) ? ChatColor.translateAlternateColorCodes('&', Main.getInstance().getMessages().getString(str.toUpperCase())) : "");
        }
        
        public MessagePart replace(final Object placeholder, final Object key) {
            this.str = this.str.replace(placeholder.toString(), key.toString());
            return this;
        }
        
        public String getPart() {
            return this.str;
        }
    }
}
