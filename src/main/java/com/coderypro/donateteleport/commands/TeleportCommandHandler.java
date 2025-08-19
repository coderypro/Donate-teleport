package com.coderypro.donateteleport.commands;

import com.coderypro.donateteleport.DonateTeleportPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles the main /dtp command and its subcommands
 */
public class TeleportCommandHandler implements CommandExecutor {
    
    private final DonateTeleportPlugin plugin;
    
    public TeleportCommandHandler(DonateTeleportPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            showHelp(player);
            return true;
        }
        
        String subcommand = args[0].toLowerCase();
        
        switch (subcommand) {
            case "spawn":
                return handleSpawn(player);
            case "home":
                return handleHome(player);
            case "vip":
                return handleVip(player);
            case "premium":
                return handlePremium(player);
            case "help":
                showHelp(player);
                return true;
            default:
                player.sendMessage(ChatColor.RED + "Unknown subcommand: " + subcommand);
                player.sendMessage(ChatColor.YELLOW + "Use '/dtp help' for available commands");
                return true;
        }
    }
    
    private void showHelp(Player player) {
        player.sendMessage(ChatColor.GOLD + "=== DonateTeleport Commands ===");
        player.sendMessage(ChatColor.YELLOW + "/dtp spawn" + ChatColor.WHITE + " - Teleport to spawn");
        
        if (player.hasPermission("donateteleport.vip")) {
            player.sendMessage(ChatColor.YELLOW + "/dtp home" + ChatColor.WHITE + " - Teleport to your home");
            player.sendMessage(ChatColor.YELLOW + "/dtp vip" + ChatColor.WHITE + " - Teleport to VIP area");
        }
        
        if (player.hasPermission("donateteleport.premium")) {
            player.sendMessage(ChatColor.YELLOW + "/dtp premium" + ChatColor.WHITE + " - Teleport to premium area");
        }
        
        if (player.hasPermission("donateteleport.admin")) {
            player.sendMessage(ChatColor.YELLOW + "/dtpreload" + ChatColor.WHITE + " - Reload configuration");
        }
    }
    
    private boolean handleSpawn(Player player) {
        if (!player.hasPermission("donateteleport.use")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }
        
        if (plugin.isOnCooldown(player)) {
            long timeLeft = plugin.getCooldownTimeLeft(player);
            player.sendMessage(ChatColor.RED + "You must wait " + timeLeft + " seconds before teleporting again!");
            return true;
        }
        
        Location spawn = player.getWorld().getSpawnLocation();
        player.teleport(spawn);
        player.sendMessage(ChatColor.GREEN + "Teleported to spawn!");
        plugin.setCooldown(player);
        
        return true;
    }
    
    private boolean handleHome(Player player) {
        if (!player.hasPermission("donateteleport.vip")) {
            player.sendMessage(ChatColor.RED + "You need VIP rank to use this command!");
            return true;
        }
        
        if (plugin.isOnCooldown(player)) {
            long timeLeft = plugin.getCooldownTimeLeft(player);
            player.sendMessage(ChatColor.RED + "You must wait " + timeLeft + " seconds before teleporting again!");
            return true;
        }
        
        Location home = player.getBedSpawnLocation();
        if (home == null) {
            home = player.getWorld().getSpawnLocation();
            player.sendMessage(ChatColor.YELLOW + "No home set, teleporting to spawn instead!");
        } else {
            player.sendMessage(ChatColor.GREEN + "Teleported to home!");
        }
        
        player.teleport(home);
        plugin.setCooldown(player);
        
        return true;
    }
    
    private boolean handleVip(Player player) {
        if (!player.hasPermission("donateteleport.vip")) {
            player.sendMessage(ChatColor.RED + "You need VIP rank to use this command!");
            return true;
        }
        
        if (plugin.isOnCooldown(player)) {
            long timeLeft = plugin.getCooldownTimeLeft(player);
            player.sendMessage(ChatColor.RED + "You must wait " + timeLeft + " seconds before teleporting again!");
            return true;
        }
        
        Location vipLocation = plugin.getVipLocation();
        if (vipLocation == null) {
            player.sendMessage(ChatColor.RED + "VIP location not configured! Contact an administrator.");
            return true;
        }
        
        player.teleport(vipLocation);
        player.sendMessage(ChatColor.GOLD + "Teleported to VIP area!");
        plugin.setCooldown(player);
        
        return true;
    }
    
    private boolean handlePremium(Player player) {
        if (!player.hasPermission("donateteleport.premium")) {
            player.sendMessage(ChatColor.RED + "You need Premium rank to use this command!");
            return true;
        }
        
        if (plugin.isOnCooldown(player)) {
            long timeLeft = plugin.getCooldownTimeLeft(player);
            player.sendMessage(ChatColor.RED + "You must wait " + timeLeft + " seconds before teleporting again!");
            return true;
        }
        
        Location premiumLocation = plugin.getPremiumLocation();
        if (premiumLocation == null) {
            player.sendMessage(ChatColor.RED + "Premium location not configured! Contact an administrator.");
            return true;
        }
        
        player.teleport(premiumLocation);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Teleported to Premium area!");
        plugin.setCooldown(player);
        
        return true;
    }
}