package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    ConsoleCommandSender console = getServer().getConsoleSender();

    @Override
    public void onEnable() {
        EnableMessage();
        FileConfiguration config = getConfig();
        config.addDefault("CanExplode", true);
        config.addDefault("CanSpawnLlama", true);
        config.addDefault("CanGenerateTree", true);
        config.addDefault("CanExplode", true);
        config.addDefault("CanSetDay", true);
        config.addDefault("CanSetNight", true);
        config.addDefault("CanSpawnCreepers", true);
        config.addDefault("CanDropDiamonds", true);
        config.addDefault("CanTeleportPlayer", true);
        config.addDefault("CanDropBomBow", true);
        config.addDefault("CanDropStickOfFire", true);
        config.addDefault("CanCanDropTelebow", true);
        config.addDefault("PlaySound", true);
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new SuperEgg(this), this);
        getServer().getPluginManager().registerEvents(new Wand(), this);
        getServer().getPluginManager().registerEvents(new TeleportingBow(), this);
        this.getCommand("surpriseeggs").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {

    }


    public void EnableMessage() {
        console.sendMessage(ChatColor.GREEN + Decor());
        console.sendMessage("");
        console.sendMessage(ChatColor.YELLOW + "Suprise Eggs");
        console.sendMessage(ChatColor.GREEN + "Made by s5y");
        console.sendMessage(ChatColor.GREEN + "Version: " + ChatColor.WHITE + "1.0.5");
        console.sendMessage("");
        console.sendMessage(ChatColor.GREEN + Decor());
    }

    public String Decor() {
        String Return_Value = "";
        for (int i = 0; i < 15; i++) {
            Return_Value = Return_Value + "*";
        }

        return (Return_Value);
    }
}