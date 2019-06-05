package com.gainee.announcements.util;

import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.*;
import java.util.concurrent.*;

import org.bukkit.inventory.meta.*;

import com.gainee.announcements.*;

public class ItemStackUtils
{
    private static List<String> replaceColors(final List<String> list) {
        final List<String> listTemp = new ArrayList<String>();
        for (String s : list) {
            s = ChatColor.translateAlternateColorCodes('&', s);
            listTemp.add(s);
        }
        return listTemp;
    }
    
    @SuppressWarnings("unchecked")
	public static ItemStack load(final Map<String, Object> keys) {
        try {
            ItemStack stack = null;
            String item = "";
            if (keys.containsKey("material")) {
                if (keys.get("material") instanceof List) {
                    final List<String> list = (List<String>) keys.get("material");
                    item = list.get(keys.containsKey("index") ? ((int)keys.get("index")) : ThreadLocalRandom.current().nextInt(list.size()));
                }
                else {
                    item = keys.get("material").toString();
                }
            }
            if (keys.containsKey("material") && keys.containsKey("amount")) {
                stack = Main.getInstance().getEss().getItemDb().get(item, Integer.parseInt(keys.get("amount").toString()));
            }
            else {
                stack = Main.getInstance().getEss().getItemDb().get(item, 1);
            }
            final ItemMeta meta = stack.getItemMeta();
            if (keys.containsKey("name")) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', keys.get("name").toString()));
            }
            if (keys.containsKey("playerhead")) {
                ((SkullMeta)meta).setOwner(keys.get("playerhead").toString());
            }
            if (keys.containsKey("lore")) {
                final List<String> lore = replaceColors((List<String>) keys.get("lore"));
                meta.setLore((List<String>)lore);
            }
            if (keys.containsKey("enchants")) {
                final List<String> enchants = (List<String>) keys.get("enchants");
                for (final String s : enchants) {
                    final String[] parts = s.split(":");
                    if (EnchantUtils.argsToEnchant(parts[0]) == null) {
                        continue;
                    }
                    if (meta instanceof EnchantmentStorageMeta) {
                        ((EnchantmentStorageMeta)meta).addStoredEnchant(EnchantUtils.argsToEnchant(parts[0]), Integer.parseInt(parts[1]), true);
                    }
                    else {
                        meta.addEnchant(EnchantUtils.argsToEnchant(parts[0]), Integer.parseInt(parts[1]), true);
                    }
                }
            }
            stack.setItemMeta(meta);
            if (keys.containsKey("enchanted")) {
                final boolean enchanted = Boolean.valueOf(keys.get("enchanted").toString());
                if (enchanted) {
                    EnchantGlow.addGlow(stack);
                }
            }
            return stack;
        }
        catch (Exception ignore) {
            Main.getInstance().getLogger().severe(ChatColor.stripColor(ignore.getMessage()));
            return null;
        }
    }
    
    @SuppressWarnings("deprecation")
	public static boolean isSimilar(final ItemStack item, final ItemStack compare) {
        if (item == null || compare == null) {
            return false;
        }
        if (item == compare) {
            return true;
        }
        if (item.getTypeId() != compare.getTypeId()) {
            return false;
        }
        if (item.getDurability() != compare.getDurability()) {
            return false;
        }
        if (item.hasItemMeta() != compare.hasItemMeta()) {
            return false;
        }
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            if (item.getItemMeta().hasDisplayName() != compare.getItemMeta().hasDisplayName()) {
                return false;
            }
            if (!item.getItemMeta().getDisplayName().equals(compare.getItemMeta().getDisplayName())) {
                return false;
            }
        }
        return true;
    }
}
