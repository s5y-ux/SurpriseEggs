package net.ddns.vcccd;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class TridentOfRandomness{
	public TridentOfRandomness(Player player) {
		ItemStack TridentOfRandomness = new ItemStack(Material.TRIDENT);
		ItemMeta TridentOfRandomnessData = TridentOfRandomness.getItemMeta();
		TridentOfRandomnessData.setDisplayName(ChatColor.YELLOW + "Trident Of The Drowned");
		TridentOfRandomness.setItemMeta(TridentOfRandomnessData);
		player.getInventory().setItemInMainHand(TridentOfRandomness);
	}
}
