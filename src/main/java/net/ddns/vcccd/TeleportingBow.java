package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportingBow implements Listener {

	//Creates the variables for determining the type of the bow
    Player refPlayer;
    boolean telebow = false;
    boolean explodeBow = false;

    @EventHandler
    
    //This is used to store information and execute code before the bow is shot
    public void onTelebowEvent(EntityShootBowEvent event) {
    	
    	//We want to make sure its a player and not a skeleton
        if (event.getEntity() instanceof Player) {
        	
        	//Stores the variables for the bow and player information
            Player player = (Player) event.getEntity();
            ItemStack playerBow = event.getBow();
            ItemMeta bowData = playerBow.getItemMeta();
            
            //Stores the global player
            refPlayer = player;

            //Checks metadata to see if the bow is indeed a Teleport bow
            if (bowData.getDisplayName().equals(ChatColor.LIGHT_PURPLE + "TeleBow")) {
            	
            	//If so, we change the global bow type
                telebow = true;
                explodeBow = false;
                
                //And we play the sound for the teleport bow
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 0);
                
                //Otherwise, if the bow is a BomBow
            } else if (bowData.getDisplayName().equals(ChatColor.RED + "BomBow")) {
            	
            	//We change the global bow type accordingly
                explodeBow = true;
                telebow = false;
                
                //We get the location of the player and the world the arrow was shot in
                Location playerLoc = player.getLocation();
                World world = player.getWorld();

                //We then spawn the particles at the player
                for (int i = -5; i < 5; i++) {
                    world.spawnParticle(Particle.CLOUD, playerLoc.getX() + i, playerLoc.getY() + i, playerLoc.getZ() + i, 5);
                }

                //And then we play the TNT Sound
                player.playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, 100, 0);
            } else {
            	
            	//Sets the bow type to the standard bow (Not a special bow)
                telebow = false;
                explodeBow = false;
            }
        }
    }

    @EventHandler
    public void onTeleportToArrow(ProjectileHitEvent event) {
    	
    	//Checks to make sure the Projectile comes from an Arrow
        if (event.getEntity() instanceof Arrow) {
        	
        	//If so we need to check the bow type
            if (telebow) {
            	
            	//If it is indeed a telebow we take the location and world of the arrow
                Location arrowLoc = event.getEntity().getLocation();
                World arrowWorld = event.getEntity().getWorld();
                
                //We then teleport the player to that location
                refPlayer.teleport(arrowLoc);

                //And then we spawn the particles at the updated location of the player (i.e the arrow)
                for (int i = -5; i < 5; i++) {
                    arrowWorld.spawnParticle(Particle.PORTAL, arrowLoc.getX() + i, arrowLoc.getY() + i, arrowLoc.getZ() + i, 10);
                }
                telebow = false;
                explodeBow = false;
                
            //But on the condition it is a bomb bow
            } else if (explodeBow) {
            	
            	//We still need the arrow location and world
                Location arrowLoc = event.getEntity().getLocation();
                World arrowWorld = event.getEntity().getWorld();
                
                //But this time, we spawn lava particles
                for (int i = -5; i < 5; i++) {
                    arrowWorld.spawnParticle(Particle.LAVA, arrowLoc.getX() + i, arrowLoc.getY() + i, arrowLoc.getZ() + i, 5);
                }

                //And create an explosion at the arrow location
                arrowWorld.createExplosion(arrowLoc, 5);
                telebow = false;
                explodeBow = false;
            } else {
            	telebow = false;
                explodeBow = false;
            }
        }
    }

}