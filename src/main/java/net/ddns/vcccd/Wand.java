package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Wand implements Listener {

    @EventHandler
    public void wand(PlayerInteractEvent event) {
    	
    	//Reference variables for accessing player, wand, and position in the event
    	//That we use the wand.
        Player player = event.getPlayer();
        ItemStack wand = event.getItem();
        Location player_position = player.getLocation();
        
        //We want to increment the player position on the Y axis by one
        //for the Fire charge not to hit the ground
        player_position.setY(player_position.getY() + 1);

        //Used to reference the world that the player is using 
        //The stick of fire in
        World player_world = player.getWorld();
        
        //If it is indeed a stick, and the stick of fire
        if (wand.getType() == Material.STICK && event.getAction() == Action.RIGHT_CLICK_AIR && wand.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lStick Of Fire!"))) {
            
        	//Init our doubles for the spawn particle method location argument
        	double X, Y, Z;
        	
        	//Then assign the coordinates
            X = player_position.getX();
            Y = player_position.getY();
            Z = player_position.getZ();
            
            //Then we spawn the particles at those coordinates
            for (int i = -5; i < 5; i++) {
                player_world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, X + i, Y + i, Z + i, 10);
            }
            
            //Finally, we summon the fireball
            player_world.spawnEntity(player_position, EntityType.FIREBALL);
            
            //And we play the Anvil sound effect.
            player.playSound(player, Sound.BLOCK_ANVIL_HIT, 100, 0);

        } else {
        	assert true;
        }
    }

}