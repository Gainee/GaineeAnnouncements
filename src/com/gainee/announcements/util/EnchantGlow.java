package com.gainee.announcements.util;

import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;
import java.lang.reflect.*;

public class EnchantGlow extends EnchantmentWrapper
{
    private static Enchantment glow;
    
    public EnchantGlow(final int id) {
        super(id);
    }
    
    public boolean canEnchantItem(final ItemStack item) {
        return true;
    }
    
    public boolean conflictsWith(final Enchantment other) {
        return false;
    }
    
    public EnchantmentTarget getItemTarget() {
        return null;
    }
    
    public int getMaxLevel() {
        return 10;
    }
    
    public String getName() {
        return "Glow";
    }
    
    public int getStartLevel() {
        return 1;
    }
    
    public static Enchantment getGlow() {
        try {
            if (EnchantGlow.glow != null) {
                return EnchantGlow.glow;
            }
            final Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(EnchantGlow.glow = (Enchantment)new EnchantGlow(255));
            return EnchantGlow.glow;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void addGlow(final ItemStack item) {
        try {
            final Enchantment glow = getGlow();
            item.addEnchantment(glow, 1);
        }
        catch (Exception ex) {}
    }
}
