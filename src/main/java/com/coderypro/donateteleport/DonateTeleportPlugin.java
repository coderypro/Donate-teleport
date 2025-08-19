package com.coderypro.donateteleport;

import com.coderypro.donateteleport.commands.AdminCommandHandler;
import com.coderypro.donateteleport.commands.TeleportCommandHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DonateTeleportPlugin extends JavaPlugin {
    
    private Map<UUID, Long> cooldowns;
    private FileConfiguration config;
    private TeleportCommandHandler commandHandler;
    private AdminCommandHandler adminCommandHandler;
    
    @Override
    public void onEnable() {
        this.cooldowns = new HashMap<>();
        
        // Save default config if it doesn't exist
        saveDefaultConfig();
        this.config = getConfig();
        
        // Initialize command handlers
        this.commandHandler = new TeleportCommandHandler(this);
        this.adminCommandHandler = new AdminCommandHandler(this);
        
        getLogger().info("DonateTeleport plugin has been enabled!");
        
        // Register commands
        getCommand("dtp").setExecutor(commandHandler);
        getCommand("dtpreload").setExecutor(this);
        getCommand("dtpadmin").setExecutor(adminCommandHandler);
    }
    
    @Override
    public void onDisable() {
        getLogger().info("DonateTeleport plugin has been disabled!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("dtpreload")) {
            return handleReloadCommand(sender);
        }
        return false;
    }
    
    private boolean handleReloadCommand(CommandSender sender) {
        if (!sender.hasPermission("donateteleport.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }
        
        reloadConfig();
        this.config = getConfig();
        sender.sendMessage(ChatColor.GREEN + "DonateTeleport configuration reloaded!");
        
        return true;
    }
    
    /**
     * Check if a player is on cooldown
     */
    public boolean isOnCooldown(Player player) {
        if (player.hasPermission("donateteleport.admin")) {
            return false; // Admins bypass cooldowns
        }
        
        UUID playerId = player.getUniqueId();
        if (!cooldowns.containsKey(playerId)) {
            return false;
        }
        
        long lastTeleport = cooldowns.get(playerId);
        long cooldownTime = config.getLong("cooldown", 30) * 1000; // Convert to milliseconds
        
        return (System.currentTimeMillis() - lastTeleport) < cooldownTime;
    }
    
    /**
     * Get remaining cooldown time for a player
     */
    public long getCooldownTimeLeft(Player player) {
        UUID playerId = player.getUniqueId();
        if (!cooldowns.containsKey(playerId)) {
            return 0;
        }
        
        long lastTeleport = cooldowns.get(playerId);
        long cooldownTime = config.getLong("cooldown", 30) * 1000;
        long timeLeft = cooldownTime - (System.currentTimeMillis() - lastTeleport);
        
        return Math.max(0, timeLeft / 1000); // Convert to seconds
    }
    
    /**
     * Set cooldown for a player
     */
    public void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }
    
    /**
     * Get the VIP teleportation location
     */
    public Location getVipLocation() {
        if (!config.contains("locations.vip")) {
            return null;
        }
        
        String worldName = config.getString("locations.vip.world");
        double x = config.getDouble("locations.vip.x");
        double y = config.getDouble("locations.vip.y");
        double z = config.getDouble("locations.vip.z");
        float yaw = (float) config.getDouble("locations.vip.yaw", 0.0);
        float pitch = (float) config.getDouble("locations.vip.pitch", 0.0);
        
        World world = getServer().getWorld(worldName);
        if (world == null) {
            getLogger().warning("VIP location world '" + worldName + "' not found!");
            return null;
        }
        
        return new Location(world, x, y, z, yaw, pitch);
    }
    
    /**
     * Get the Premium teleportation location
     */
    public Location getPremiumLocation() {
        if (!config.contains("locations.premium")) {
            return null;
        }
        
        String worldName = config.getString("locations.premium.world");
        double x = config.getDouble("locations.premium.x");
        double y = config.getDouble("locations.premium.y");
        double z = config.getDouble("locations.premium.z");
        float yaw = (float) config.getDouble("locations.premium.yaw", 0.0);
        float pitch = (float) config.getDouble("locations.premium.pitch", 0.0);
        
        World world = getServer().getWorld(worldName);
        if (world == null) {
            getLogger().warning("Premium location world '" + worldName + "' not found!");
            return null;
        }
        
        return new Location(world, x, y, z, yaw, pitch);
    }
    
    /**
     * Set VIP location
     */
    public void setVipLocation(Location location) {
        config.set("locations.vip.world", location.getWorld().getName());
        config.set("locations.vip.x", location.getX());
        config.set("locations.vip.y", location.getY());
        config.set("locations.vip.z", location.getZ());
        config.set("locations.vip.yaw", location.getYaw());
        config.set("locations.vip.pitch", location.getPitch());
        saveConfig();
    }
    
    /**
     * Set Premium location
     */
    public void setPremiumLocation(Location location) {
        config.set("locations.premium.world", location.getWorld().getName());
        config.set("locations.premium.x", location.getX());
        config.set("locations.premium.y", location.getY());
        config.set("locations.premium.z", location.getZ());
        config.set("locations.premium.yaw", location.getYaw());
        config.set("locations.premium.pitch", location.getPitch());
        saveConfig();
    }
}