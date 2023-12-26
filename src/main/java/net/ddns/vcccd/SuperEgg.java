package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;


import java.util.Random;
import java.util.ArrayList;

public class SuperEgg implements Listener {
    private final Main main;

    //Boolean used to check if the egg being thrown is a surprise egg
    Boolean TrueEgg = false;

    public SuperEgg(Main main) {
        this.main = main;
    }

    public void PlaySound(Boolean bool, Sound sound, Player player) {
    	if(bool) {
    		player.playSound(player.getLocation(), sound, 100, 1);
    	}
    }
    
    //Function RNG returns a Random Number
    public int RNG(int max_value) {
        Random randomnumber = new Random();
        int return_val = randomnumber.nextInt(max_value);
        return (return_val);
    }

    /*
    ExplodeInStyle takes a particle, egg, and world
    check the coords of that egg and spawns particles
    in the world according to the location of the egg
    */
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

    /*
     * This event is used the moment the egg is about to leave the hand.
     * Depending on if the egg that was in the hand is a surprise egg,
     * before the event to handle the surprise eggs executes, we set
     * the true egg variable to true for any surprise egg functionality.
     */
    public void PreEggTrow(PlayerInteractEvent event) {

        //Stores the player in a reference variable
        Player player = event.getPlayer();

        //Store the item (an egg) in a reference variable
        ItemStack Egg = player.getInventory().getItemInMainHand();

        //Checks the Item Meta for the name of the Egg
        if(Egg.getItemMeta() != null) {
        if (Egg.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lSurprise Egg"))) {
            TrueEgg = true;
        } else {
            TrueEgg = false;
        }
    } else {
    	TrueEgg = false;
    }
    } 

    @EventHandler

    /*
     * Now its time to handle when the egg hits the ground.
     * Here is all of the different things the surprise egg can do...
     */

    public void onEggThrow(PlayerEggThrowEvent event) {
        FileConfiguration config = main.getConfig();

        if (TrueEgg == true) {

            //Here we set all of the different custom items that the egg can give us
            ItemStack DiamondDrop = new ItemStack(Material.DIAMOND);

            ItemStack StickOfFire = new ItemStack(Material.STICK);
            ItemMeta StickOfFire_Meta = StickOfFire.getItemMeta();
            StickOfFire_Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lStick Of Fire!"));
            StickOfFire.setItemMeta(StickOfFire_Meta);

            ItemStack TeleBow = new ItemStack(Material.BOW);
            ItemMeta TeleBowData = TeleBow.getItemMeta();
            TeleBowData.setDisplayName(ChatColor.LIGHT_PURPLE + "TeleBow");
            TeleBow.setItemMeta(TeleBowData);

            ItemStack BomBow = new ItemStack(Material.BOW);
            ItemMeta BomBowData = BomBow.getItemMeta();
            BomBowData.setDisplayName(ChatColor.RED + "BomBow");
            BomBow.setItemMeta(BomBowData);

            //Sets Hatching to False so nothing is Hatched from the base egg
            event.setHatching(false);

            //Init reference variables for player, world, and egg in the Throw event
            Player player = event.getPlayer();
            World event_world = player.getWorld();
            Egg Thrown_Egg = event.getEgg();

            //Stores the Coords of the thrown egg on collision
            Location egg = Thrown_Egg.getLocation();


            //Random Events Section

            /*
             * Notice the use of RNG() to generate a random number.
             * If you want more events, you must change the argument.
             */

            //This array is filled with all of the different cases according to the config file
            ArrayList < Integer > CaseArray = new ArrayList < Integer > ();
            EntityType[] EntityArray = {EntityType.HORSE, EntityType.BLAZE, EntityType.CAMEL, EntityType.DONKEY,
            		EntityType.CAT, EntityType.RABBIT, EntityType.SKELETON_HORSE};

            boolean[] Config_Keys = {
                config.getBoolean("CanExplode"),
                config.getBoolean("CanSpawnCreature"),
                config.getBoolean("CanGenerateTree"),
                config.getBoolean("CanSetDay"),
                config.getBoolean("CanSetNight"),
                config.getBoolean("CanSpawnCreepers"),
                config.getBoolean("CanDropDiamonds"),
                config.getBoolean("CanDropStickOfFire"),
                config.getBoolean("CanTeleportPlayer"),
                config.getBoolean("CanCanDropTelebow"),
                config.getBoolean("CanDropBomBow"),
                config.getBoolean("CanLaunchPlayer"),
                config.getBoolean("CanSpawnTrident")
            };

            //We iterate over the config values and if true place the corresponding index in the CaseArray
            for (int index = 0; index < Config_Keys.length; index++) {
                if (Config_Keys[index] == true) {
                    CaseArray.add(index);
                }
            }

            //Checks to see if the size of the array is zero, this means all the values
            //In the config file are set to false...
            if (CaseArray.size() == 0) {

                //If so, we tell the player to look at the config
                player.sendMessage("Dude... The Config...");

                //Otherwise
            } else {

                //Time to boogie with the various random outcomes
                switch (CaseArray.get(RNG(CaseArray.size()))) {
                    case 0:
                        //This is literally just an explosion...
                        //if(config.getBoolean("CanExplode")) {
                        event_world.createExplosion(egg, 5, false);
                        explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_HURT, player);
                        break;
                    case 1:
                        //This was early in the development. This just spawns an entity
                        event_world.spawnEntity(egg, EntityArray[RNG(EntityArray.length)]);
                        explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 2:
                        //This one generates a tree in the world. Pretty cool!

                        event_world.generateTree(egg, TreeType.TREE);
                        explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 3:
                        //This one is a little disruptive, will add in configuration file
                        //If the Admins want to change this.
                        //Sets world to a cold and rainy night.

                        event_world.setClearWeatherDuration(0);
                        explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
                        player.sendTitle(ChatColor.BLUE + "On a cold night...", null, 3, 40, 3);
                        event_world.setTime(15000);
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 4:
                        //This one does the opposite of the previous case
                        //It converts it to a nice warm morning.

                        event_world.setClearWeatherDuration(10000);
                        explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
                        player.sendTitle(ChatColor.YELLOW + "On a warm day...", null, 3, 40, 3);
                        event_world.setTime(0);
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 5:
                        //This one spawns creepers.

                        //Embedded for loop to generate a box of creepers on the X and Z axis

                        for (int i = 0; i < 4; i++) {
                            egg.setX(egg.getX() + (i));
                            event_world.spawnEntity(egg, EntityType.CREEPER);
                            for (int j = -2; j < 2; j++) {
                                egg.setZ(egg.getZ() + (j));
                                event_world.spawnEntity(egg, EntityType.CREEPER);
                            }
                        }
                        explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
                        player.sendMessage(ChatColor.GREEN + "Good Luck With That :)");
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_HURT, player);
                        break;
                    case 6:
                        //This one drops three diamonds.

                        for (int i = 0; i < 3; i++) {
                            event_world.dropItem(egg, DiamondDrop);
                        }
                        explodeInStyle(Particle.CRIT_MAGIC, Thrown_Egg, event_world);
                        player.sendMessage(ChatColor.BLUE + "Wow Diamonds!!!");
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 7:
                        //This one summons the Stick of Fire
                        //Note the Custom Items

                        event_world.dropItem(egg, StickOfFire);
                        explodeInStyle(Particle.CAMPFIRE_SIGNAL_SMOKE, Thrown_Egg, event_world);
                        player.sendMessage(ChatColor.RED + "Behold... The Stick Of FIRE!");
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 8:
                        //This one teleports the player.

                        player.sendMessage(ChatColor.YELLOW + "Woosh!");
                        player.teleport(egg);
                        explodeInStyle(Particle.CLOUD, Thrown_Egg, event_world);
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                        /*
                         * The Next two cases are for the teleporting bow
                         * and the Bomb bow, If you wish to change
                         * the functionality of these two items please
                         * refer to the Teleporting bow class. I know
                         * It only says teleporting bow, the bomb bow was added
                         * on a whim. I would tell you that Id make a seprate
                         * class for the bomb bow but Id be lying, I know
                         * that this plugin is a little scrappy but it was
                         * mostly for me to get familiar with the spigot API
                         */
                    case 9:
                        //As said in the above comment, this one drops the teleporting bow

                        event_world.dropItem(egg, TeleBow);
                        explodeInStyle(Particle.PORTAL, Thrown_Egg, event_world);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lBehold! The &d&lTeleBow!"));
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 10:
                        //And this one drops the Exploding bow known as BombBow	           
                        event_world.dropItem(egg, BomBow);
                        explodeInStyle(Particle.LAVA, Thrown_Egg, event_world);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lBehold! The &c&lBomBow!"));
                        PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                        break;
                    case 11:
                    	PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_HURT, player);
                    	Vector Direction = new Vector(0, config.getInt("LaunchPlayerAmount"), 0);
                    	explodeInStyle(Particle.CLOUD, player, event_world);
                    	player.setVelocity(Direction);
                    	break;
                    case 12:
                    	TridentOfRandomness PlayerTrident = new TridentOfRandomness(player);
                    	PlaySound(config.getBoolean("PlaySound"), Sound.ENTITY_VILLAGER_AMBIENT, player);
                    	explodeInStyle(Particle.CLOUD, player, event_world);
                    	break;
                    default:
                        player.sendMessage("Dude... The Config...");
                }


            }
        }



    }
    
}