package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
        	//Creates a Surprise Egg 
        	ItemStack SurpriseEgg = new ItemStack(Material.EGG);
        	ItemMeta SurpriseEggData = SurpriseEgg.getItemMeta();
        	SurpriseEggData.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lSurprise Egg"));
        	SurpriseEgg.setItemMeta(SurpriseEggData);
        	
        	//References The Player
        	Player player = (Player) sender;
        	
        	//Tells player to enjoy the surprise egg
        	player.sendMessage(ChatColor.YELLOW + "Enjoy!");
        	
        	//Gives the Player the Surprise Egg
        	player.getInventory().setItemInMainHand(SurpriseEgg);
        	
        } else {
        	
        //If the sender is not a Player, we give the author and version Number
    	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lVersion: &r&fV1.0.1"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lAuthor: &r&fs5y"));
        }
        return (true);
    }

}