package de.hulaa.HMClevels;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class HMClevels extends JavaPlugin implements Listener {
	
	private static double playerHealthModifier = 1.0D;
	private static double playerADModifier = 1.0D;
	
	
	@Override
	public void onEnable() {
		
		Bukkit.getServer().getPluginManager().registerEvents(this,this);
		
		System.out.println("[HMC] HMClevels 0.1 erfolgreich geladen.");


	}
	
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		Player player = (Player) event.getPlayer();
		PlayerInventory inv = player.getInventory();
		boolean weaponEquipped = (inv.getItem(0)!=null);
		
		
		if(weaponEquipped && inv.getItem(0).getType() == Material.DIAMOND_SWORD ){
		setWeaponDamage(player, 2.0D);
		EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();

		player.sendMessage("Diamond Sword equipped - new Attack Damage=" + p.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue());
		}
	}
	
	@EventHandler
    public void onPlayerLevelUpEvent(PlayerLevelChangeEvent event){
		
		Player player = event.getPlayer();
		int level = player.getLevel();
	
		LevelUp(player,level);
		
		
	}
	
	@EventHandler
	public void onPlayerLevelcappedEvent(PlayerExpChangeEvent event){
		
		Player player = event.getPlayer();
		
		if(player.getLevel() >=50){
			player.setExp(0);
		}else{
			
			player.sendMessage("+"+event.getAmount()+"XP");
		}

	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		
		player.setHealthScale(40D);
		player.sendMessage("§2[HMC] §3Welcome back to Homohausen!");

	}
		

	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		Player player = (Player) sender;



		if (cmd.getName().equalsIgnoreCase("hmclevel")) {

			
			String inputLevel = args[0];
			int level = Integer.parseInt(inputLevel);

			if (level >= 0 && level <= 50)
			{
				player.setLevel(level);
				LevelUp(player,level);
				return true;
				
			} else
			{
				player.sendMessage("§4[HMC] §4Du musst einen Wert zwischen 1 und 50 eingeben! Beispiel: /hmclevel 36");
				return true;
			}
		}
		
		return true;
		
	}
	
	
	public static void LevelUp(Player player, int level){
		
		player.setHealthScale(40D);
		
		EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
		
		double health = playerHealthModifier*AttributeSetter.setPlayerHealth(level);
		double ad = playerADModifier*AttributeSetter.setPlayerAD(level);

		p.getAttributeInstance(GenericAttributes.maxHealth).setValue(health);
		p.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(ad);


		player.sendMessage("§2[HMC] §3Level auf " + level + " gesetzt! HP= "+health+ " AD= "+ad);
		
	}
	
	
	public static double getPlayerHealth(Player player){
		
		EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
		return p.getAttributeInstance(GenericAttributes.maxHealth).getValue();
		
	}
	
	public static double getPlayerAD(Player player){
		EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
		return p.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
	}
	

	public static void setWeaponDamage(Player player, double modifier){
		
		EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
		double AD = p.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
		
		p.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(AD*modifier);
	
	}

}
	
	

