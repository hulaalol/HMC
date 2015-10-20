package me.hulaa.info;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;




public class HMCAdmin extends JavaPlugin{
	

	@Override
	public void onEnable() {
		System.out.println("[HMC] Hulaa MineCraft 1.1 successfully loaded!");
	}

	@Override
	public void onDisable() {
		System.out.println("[HMC] Hulaa MineCraft 1.1 closed!");
	}


	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("info")) {
			player.sendMessage("§2[INFO] §3 Welcome to Hulaa & Chrizzlyx Minecraft Server!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("speed")) {


			String walkSpeed = args[0];
			float value = Float.parseFloat(walkSpeed);

			if (value >= 0 && value <= 1)
			{
				player.setWalkSpeed(value);
				player.setFlySpeed(value);
				player.sendMessage("§2[HMC] §3Set speed to " + value);
				return true;	
			} else
			{
				player.sendMessage("§4[HMC] §4Enter a value between 0.0 and 1.0!");
				return true;
			}

		}
		
		if (cmd.getName().equalsIgnoreCase("health")) {

			double health = Double.parseDouble(args[0]);
			EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
			p.getAttributeInstance(GenericAttributes.maxHealth).setValue(health);
			player.sendMessage("§2[HMC] §3Set health to "+(Math.round(p.getAttributeInstance(GenericAttributes.maxHealth).getValue())));
			return true;
			}
		
		if (cmd.getName().equalsIgnoreCase("ad")){
			
			double ad = Double.parseDouble(args[0]);
			EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
			p.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(ad);
			player.sendMessage("§2[HMC] §3Set Attack Damage to "+(Math.round(p.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue())));
			return true;
		}
		
		
		if (cmd.getName().equalsIgnoreCase("c")){
			
			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage("§2[HMC] §3Set gamemode to Creative");
			return true;
			}
	
		if (cmd.getName().equalsIgnoreCase("s")){
		
		player.setGameMode(GameMode.SURVIVAL);
		player.sendMessage("§2[HMC] §3Set gamemode to Survival");
		return true;
			}
		
		
		

		if (cmd.getName().equalsIgnoreCase("heal")) {
			
			EntityLiving p = (EntityLiving) ((CraftEntity) player).getHandle();
			
			p.setHealth(p.getMaxHealth());
			player.setFoodLevel(20);
			player.sendMessage("§2[HMC] §3Health restored!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("clearinv")) {
			player.getInventory().clear();
			player.sendMessage("§2[HMC] §3Inventory cleared!");
			return true;
		}



		if (cmd.getName().equalsIgnoreCase("town")) {
			
			if(!(args.length==0))
			{
			
				String argument  = args[0].toLowerCase();
				boolean playerTeleported = false;
				World map = player.getWorld();
				
				Towns[] towns = Towns.values();
				
				for(int i=0; i<towns.length; i++){
					
					if(towns[i].name.equalsIgnoreCase(argument)){

						player.teleport(new Location(map,towns[i].x, towns[i].y, towns[i].z));
						playerTeleported = true;
						player.sendMessage("§2[HMC] §3Teleported to "+towns[i].name+"!");
						break;
					}	

				}
				
				if(!playerTeleported){
					player.sendMessage("§4[HMC] §4Not a valid town! List of valid towns with /towninfo");
				}

			
		}
			
			return true;
		}
		
		
		if (cmd.getName().equalsIgnoreCase("towninfo")) {
			
			for(Towns t : Towns.values()){
				player.sendMessage("§3"+t.name);
			}
			
			return true;
		}

		return true;

}
	
}






