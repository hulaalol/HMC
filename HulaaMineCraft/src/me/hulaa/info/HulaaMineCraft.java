package me.hulaa.info;


import java.util.Random;



import java.util.Scanner;

import net.minecraft.server.v1_8_R3.EntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.*;


public class HulaaMineCraft extends JavaPlugin{




	@Override
	public void onEnable() {
		System.out.println("[HMC] Hulaa MineCraft 1.1 erfolgreich geladen.");
		
		
	
		






	}



// random Number generator
	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	@Override
	public void onDisable() {
		System.out.println("[HMC] Hulaa MineCraft erfolgreich deaktiviert.");
	}


	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("hmcinfo")) {
			player.sendMessage("§2[INFO] §3 Dies ist Hulaa's und Chrizzlyx Super Minecraft Server!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("hmcspeed")) {


			String walkSpeed = args[0];
			float value = Float.parseFloat(walkSpeed);

			if (value >= 0 && value <= 1)
			{
				player.setWalkSpeed(value);
				player.setFlySpeed(value);
				player.sendMessage("§2[HMC] §3Geschwindigkeit auf " + value + " erhöht!");
				return true;	
			} else
			{
				player.sendMessage("§4[HMC] §4Du musst einen Wert zwischen 0 und 1 eingeben! Beispiel: /hmcspeed 0.5");
				return true;
			}


		}

		if (cmd.getName().equalsIgnoreCase("hmcrestore")) {
			
			EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
			
			p.setHealth(p.getMaxHealth());
			player.setFoodLevel(20);
			player.sendMessage("§2[HMC] §3Du bist wieder gesund und satt!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("hmcclearinv")) {
			player.getInventory().clear();
			player.sendMessage("§2[HMC] §3Inventar wurde geleert!");
			return true;
		}



		if (cmd.getName().equalsIgnoreCase("hmctowns")) {
			
			if(!(args.length==0))
			{
			
				String town = args[0].toLowerCase();
				World map = player.getWorld();
				
				switch (town){
				
				
		
				case "arrowfall":
				Location arrowtown = new Location(map,15,80,-2705);
				player.teleport(arrowtown);
				player.sendMessage("§2[HMC] §3Du wurdest nach Arrowtown teleportiert!");
				break;
				
				case "lagoona":
				Location lagoona = new Location(map,-371,80,-2993);
				player.teleport(lagoona);
				player.sendMessage("§2[HMC] §3Du wurdest nach Lagoona teleportiert!");
				break;	
				
				case "orodom":
				Location orodom = new Location(map,-378,80,-2334);
				player.teleport(orodom);
				player.sendMessage("§2[HMC] §3Du wurdest nach Orodom teleportiert!");
				break;	
				
				default:
				player.sendMessage("§4[HMC] §4Kein gültiger Ort eingegeben! Liste gültiger Orte mit /towninfo");
				
				}
			}
			else
			{
				player.sendMessage("§4[HMC] §4Kein Ort eingegeben! Liste gültiger Orte mit /towninfo");
				return true;
			}
			
		}
		
		return true;

}

	//old version teleport
//				if (args[0].equalsIgnoreCase("arrowtown"))
//				{
//					
//				World map = player.getWorld();
//				Location arrowtown = new Location(map,15,80,-2705);
//				player.teleport(arrowtown);
//				player.sendMessage("§2[HMC] §3Du wurdest nach" + args[0] + " teleportiert!");
//				return true;
//				}
//				else
//				{
//				player.sendMessage("§4[HMC] §4Kein gültiger Ort! Liste gültiger Orte mit /towninfo");
//				return true;
//				}
//				}
	
	
}






