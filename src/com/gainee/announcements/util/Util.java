package com.gainee.announcements.util;

import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import java.util.*;
import org.bukkit.*;

public class Util
{
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static ItemStack createItem(final Material mat, final int amt, final int durability, final String name, final List<String> lore) {
        final ItemStack item = new ItemStack(mat, amt);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List)lore);
        if (durability != 0) {
            item.setDurability((short)durability);
        }
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack createItem(final Material mat, final int amt, final String name, final List<String> lore) {
        return createItem(mat, amt, 0, name, lore);
    }
    
    public static ItemStack createItem(final Material mat, final int amt, final int durability, final String name, final String... lore) {
        final List<String> l = new ArrayList<String>();
        for (final String s : lore) {
            l.add(s);
        }
        return createItem(mat, amt, durability, name, l);
    }
    
    public static ItemStack createBorder(final int color) {
        return createItem(Material.STAINED_GLASS_PANE, 1, color, color("&c&l&1&c&k&r"), new String[0]);
    }
    
    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
