package de.hulaa.hmcLOOT;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HMCloot extends JavaPlugin implements Listener {
	
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this,this);
		Bukkit.getServer().broadcastMessage("hmcLOOT 1.1 successfully loaded!");
	}
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent playerDied)
	{
		playerDied.getDrops().clear();
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent entityDied) {

		Player Killer;
		String KillerName;
		int KillerLevel;

		if (entityDied.getEntity().getKiller() instanceof Player) {
			Killer = entityDied.getEntity().getKiller();
			KillerName = entityDied.getEntity().getKiller().getDisplayName();
			KillerLevel = entityDied.getEntity().getKiller().getLevel();

			if (entityDied.getEntity() instanceof Ghast) {
				Ghast g = (Ghast) entityDied.getEntity();

				if (g.getCustomName() == "Superghast") {

					entityDied.setDroppedExp(1000);

					ItemStack Loot1 = new ItemStack(Material.DIAMOND_HELMET, 1);
					Loot1.addEnchantment(Enchantment.PROTECTION_FIRE, 1);
					Loot1.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3);

					ItemMeta Loot1meta = Loot1.getItemMeta();
					Loot1meta.setDisplayName(ChatColor.BLUE + "Hulaa Helmet");
					ArrayList<String> LoreLoot1 = new ArrayList<String>();
					LoreLoot1.add(ChatColor.ITALIC + "" + ChatColor.GOLD
							+ "Looks like new!");
					Loot1meta.setLore(LoreLoot1);
					Loot1.setItemMeta(Loot1meta);

					Killer.sendMessage(KillerName
							+ "killed a SuperGhast! Your current Level: "
							+ KillerLevel);
					entityDied
							.getEntity()
							.getWorld()
							.dropItem(entityDied.getEntity().getLocation(),
									Loot1);
					entityDied
							.getEntity()
							.getWorld()
							.dropItem(entityDied.getEntity().getLocation(),
									LootRoller.giveArmorLoot(KillerLevel));

				} else {

					// nothing

				}

			}
		}
}
}
