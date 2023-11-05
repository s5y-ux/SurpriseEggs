package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lVersion: &r&fV1.0.0"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lAuthor: &r&fs5y"));
        return (true);
    }

}