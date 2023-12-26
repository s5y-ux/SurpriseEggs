package net.ddns.vcccd;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class TridentOfRandomnessListener implements Listener {
	
	private final Main main;
	
	public TridentOfRandomnessListener(Main main) {
		this.main = main;
	}
	
	public void PlaySound(Boolean bool, Sound sound, Player player) {
    	if(bool) {
    		player.playSound(player.getLocation(), sound, 100, 1);
    	}
    }
	
	public void explodeInStyle(Particle parameter, Entity egg, World world) {

        //Stores the location of the egg in egg_loc variable
        Location egg_loc = egg.getLocation();

        //Inits doubles to reference in the argument of the spawn particle method
        double X, Y, Z;

        //Assigns the values at their respective coordinates
        X = egg_loc.getX();
        Y = egg_loc.getY();
        Z = egg_loc.getZ();

        //Spawns the particles
        for (int i = -5; i < 5; i++) {
            world.spawnParticle(parameter, X + i, Y + i, Z + i, 10);
        }
    }
	
	@EventHandler
	public void onTridentUse(PlayerInteractEvent event) {
		FileConfiguration config = main.getConfig();
		if(event.getItem().equals(null)) {
			assert true;
		} else if(config.getBoolean("TridentSummon") && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Trident Of The Drowned")) {
			Vector SpawnLocation = event.getPlayer().getLocation().toVector().add(event.getPlayer().getEyeLocation().getDirection().multiply(2));
			SpawnLocation.add(new Vector(0, 2, 0));
			Location SpawnLocationReal = SpawnLocation.toLocation(event.getPlayer().getWorld());
			Drowned mainman = (Drowned) event.getPlayer().getWorld().spawnEntity(SpawnLocationReal, EntityType.DROWNED);
			explodeInStyle(Particle.CLOUD, mainman, event.getPlayer().getWorld());
			PlaySound(true, Sound.ENTITY_VILLAGER_HURT, event.getPlayer());
		}
		
	}
}
