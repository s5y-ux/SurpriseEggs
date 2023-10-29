package net.ddns.vcccd;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import java.util.Random;

public class SuperEgg implements Listener{
	
	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent event) { 
		
		//Sets Hatching to False so nothing is Hatched from the base egg
		event.setHatching(false);
		
		//Init reference variables for player, world, and egg in the
		//Throw event
		Player player = event.getPlayer();
		World event_world = player.getWorld();
		Egg Thrown_Egg = event.getEgg();
		
		//Stores the Coords of the thrown egg on collision
		Location egg = Thrown_Egg.getLocation();

		
		//Random Events
		
		/*
		 Notice the particles in the first argument of the
		 Function "explodeInStyle". I want to create
		 an array of particles and create an arg for RNG
		 to generate random particles in the explosions.
		 */
		switch(RNG()) {
		case 0:
			event_world.createExplosion(egg, 5, false);
			explodeInStyle(Particle.CRIT_MAGIC, Thrown_Egg, event_world);
			break;
		case 1:
			event_world.spawnEntity(egg, EntityType.LLAMA);
			explodeInStyle(Particle.ENCHANTMENT_TABLE, Thrown_Egg, event_world);
			break;
		case 2:
			event_world.generateTree(egg, TreeType.BIG_TREE);
			explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
			break;
		}
		
	}
	
	//Function RNG returns a Random Number
	public int RNG() {
		Random randomnumber = new Random();
		int return_val = randomnumber.nextInt(3);
		return(return_val);
	}
	
	//ExplodeInStyle takes a particle, egg, and world
	//check the coords of that egg and spawns particles
	//in the world according to the location of the egg
	public void explodeInStyle(Particle parameter, Egg egg, World world) {
		Location egg_loc = egg.getLocation();
		double X, Y, Z;
		X = egg_loc.getX();
		Y = egg_loc.getY();
		Z = egg_loc.getZ();
		for (int i = -5 ; i < 5; i++) {
			world.spawnParticle(parameter, X + i, Y + i, Z + i, 10);
		}
	}
}
