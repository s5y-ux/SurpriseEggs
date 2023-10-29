package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	ConsoleCommandSender console = getServer().getConsoleSender();
	
	
    @Override
    public void onEnable() {
    	EnableMessage();
    	getServer().getPluginManager().registerEvents(new SuperEgg(), this);
    }
    
    @Override
    public void onDisable() {

    }
    
    public void EnableMessage() {
    	console.sendMessage(ChatColor.BLUE + Decor());
    	console.sendMessage("");
    	console.sendMessage(ChatColor.GREEN + "Suprise Eggs");
    	console.sendMessage(ChatColor.BLUE + "Made by s5y");
    	console.sendMessage(ChatColor.BLUE + "Version: " + ChatColor.WHITE + "1.0");
    	console.sendMessage("");
    	console.sendMessage(ChatColor.BLUE + Decor());
    }
    
    public String Decor() {
    	String Return_Value = "";
    	for(int i = 0; i < 15; i++) {
    		Return_Value = Return_Value + "*"; 
    	}
    	
    	return(Return_Value);
    }
}
