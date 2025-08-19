package com.coderypro.donateteleport.commands;

import com.coderypro.donateteleport.DonateTeleportPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles admin commands for setting teleportation locations
 */
public class AdminCommandHandler implements CommandExecutor {
    
    private final DonateTeleportPlugin plugin;
    
    public AdminCommandHandler(DonateTeleportPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("donateteleport.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }
        
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length < 1) {
            showAdminHelp(player);
            return true;
        }
        
        String subcommand = args[0].toLowerCase();
        
        switch (subcommand) {
            case "setvip":
                return setVipLocation(player);
            case "setpremium":
                return setPremiumLocation(player);
            case "info":
                return showLocationInfo(player);
            case "help":
                showAdminHelp(player);
                return true;
            default:
                player.sendMessage(ChatColor.RED + "Unknown admin subcommand: " + subcommand);
                showAdminHelp(player);
                return true;
        }
    }
    
    private void showAdminHelp(Player player) {
        player.sendMessage(ChatColor.GOLD + "=== DonateTeleport Admin Commands ===");
        player.sendMessage(ChatColor.YELLOW + "/dtpadmin setvip" + ChatColor.WHITE + " - Set VIP teleport location");
        player.sendMessage(ChatColor.YELLOW + "/dtpadmin setpremium" + ChatColor.WHITE + " - Set Premium teleport location");
        player.sendMessage(ChatColor.YELLOW + "/dtpadmin info" + ChatColor.WHITE + " - Show current location settings");
        player.sendMessage(ChatColor.YELLOW + "/dtpreload" + ChatColor.WHITE + " - Reload plugin configuration");
    }
    
    private boolean setVipLocation(Player player) {
        Location location = player.getLocation();
        plugin.setVipLocation(location);
        
        player.sendMessage(ChatColor.GREEN + "VIP teleport location set to your current position!");
        player.sendMessage(ChatColor.GRAY + "World: " + location.getWorld().getName());
        player.sendMessage(ChatColor.GRAY + "X: " + String.format("%.2f", location.getX()));
        player.sendMessage(ChatColor.GRAY + "Y: " + String.format("%.2f", location.getY()));
        player.sendMessage(ChatColor.GRAY + "Z: " + String.format("%.2f", location.getZ()));
        
        return true;
    }
    
    private boolean setPremiumLocation(Player player) {
        Location location = player.getLocation();
        plugin.setPremiumLocation(location);
        
        player.sendMessage(ChatColor.GREEN + "Premium teleport location set to your current position!");
        player.sendMessage(ChatColor.GRAY + "World: " + location.getWorld().getName());
        player.sendMessage(ChatColor.GRAY + "X: " + String.format("%.2f", location.getX()));
        player.sendMessage(ChatColor.GRAY + "Y: " + String.format("%.2f", location.getY()));
        player.sendMessage(ChatColor.GRAY + "Z: " + String.format("%.2f", location.getZ()));
        
        return true;
    }
    
    private boolean showLocationInfo(Player player) {
        player.sendMessage(ChatColor.GOLD + "=== DonateTeleport Location Info ===");
        
        Location vipLocation = plugin.getVipLocation();
        if (vipLocation != null) {
            player.sendMessage(ChatColor.YELLOW + "VIP Location:");
            player.sendMessage(ChatColor.GRAY + "  World: " + vipLocation.getWorld().getName());
            player.sendMessage(ChatColor.GRAY + "  X: " + String.format("%.2f", vipLocation.getX()));
            player.sendMessage(ChatColor.GRAY + "  Y: " + String.format("%.2f", vipLocation.getY()));
            player.sendMessage(ChatColor.GRAY + "  Z: " + String.format("%.2f", vipLocation.getZ()));
        } else {
            player.sendMessage(ChatColor.RED + "VIP Location: Not set");
        }
        
        Location premiumLocation = plugin.getPremiumLocation();
        if (premiumLocation != null) {
            player.sendMessage(ChatColor.YELLOW + "Premium Location:");
            player.sendMessage(ChatColor.GRAY + "  World: " + premiumLocation.getWorld().getName());
            player.sendMessage(ChatColor.GRAY + "  X: " + String.format("%.2f", premiumLocation.getX()));
            player.sendMessage(ChatColor.GRAY + "  Y: " + String.format("%.2f", premiumLocation.getY()));
            player.sendMessage(ChatColor.GRAY + "  Z: " + String.format("%.2f", premiumLocation.getZ()));
        } else {
            player.sendMessage(ChatColor.RED + "Premium Location: Not set");
        }
        
        return true;
    }
}