package de.hulaa.CustomMobs3;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySkeleton;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class AdditionalMobSpawner extends JavaPlugin {


	public static Location getSpawnLocation(Player ChoosenPlayer) {

		org.bukkit.World world = ChoosenPlayer.getWorld();
		Location SpawnLoc = ChoosenPlayer.getLocation();

		if (SpawnLoc.getBlockY() < world.getHighestBlockYAt(SpawnLoc)) {
			return null;

		} else {

			double x = SpawnLoc.getX();
			double z = SpawnLoc.getZ();

			double xAdjustment = (double) Utils.randInt(-40, 40);
			double zAdjustment = (double) Utils.randInt(-40, 40);

			SpawnLoc.setX(x + xAdjustment);
			SpawnLoc.setZ(z + zAdjustment);



			int hBlock = world.getHighestBlockYAt(SpawnLoc);
			SpawnLoc.setY((double) hBlock);

			if (SpawnLoc.getBlock().getLightLevel() <= 7) {

				
				Location CheckBlock = new Location(world,SpawnLoc.getX(), SpawnLoc.getY(), SpawnLoc.getZ());
				CheckBlock.setY(CheckBlock.getY()-1);
				Material block = CheckBlock.getBlock().getType();

				if ( block != Material.STATIONARY_WATER && block != Material.STATIONARY_LAVA && block != Material.WATER && block != Material.LAVA) {
					
					Bukkit.getServer().broadcastMessage("[BETA] SpawnBlock was:"+ block.name());
					return SpawnLoc;
					

				}else{
					Bukkit.getServer().broadcastMessage("[BETA] Spawn aborted because SpawnBlock was:"+ block.name());
				}
				return null;

			} else {
				return null;
			}

		}

	}

	public static int getSpawnLevel(int playerLevel) {

		int max = Utils.randInt(playerLevel+1, playerLevel + 3);
		int min = Utils.randInt(playerLevel - 3, playerLevel);

		if (min<1)
		{
			min = 1;
		}

		int spawnLevel = Utils.randInt(min, max);
		return spawnLevel;

	}


	public static int spawnLevel(){

		//returns
		int maxlevel;
		int minlevel;


		//int playerLevelSum=0;
		//int playerLevelAverage;
		int highestPlayer=0;
		int lowestPlayer= 1000;



		Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);

		if (players.length>1){

			for (int i = 0; i < players.length; i++) {
				//playerLevelSum += players[i].getLevel();
				if (players[i].getLevel() > highestPlayer) {
					highestPlayer = players[i].getLevel();

				}

				if (players[i].getLevel() < lowestPlayer) {
					lowestPlayer = players[i].getLevel();

				}
			}

			//playerLevelAverage = playerLevelSum/players.length;
			int levelRoller = Utils.randInt(1,100);


			if (levelRoller<15){

				maxlevel= highestPlayer+7;
				minlevel= highestPlayer-3;

				if (minlevel<1)
				{
					minlevel = 1;
				}


				int levels = Utils.randInt(minlevel, maxlevel);
				return levels;

			}else if (levelRoller>=15 && levelRoller<50){

				maxlevel= ((highestPlayer-lowestPlayer)/2)+lowestPlayer+7;
				minlevel= ((highestPlayer-lowestPlayer)/2)+lowestPlayer-8;

				if (minlevel<1)
				{
					minlevel = 1;
				}

				int levels = Utils.randInt(minlevel, maxlevel);
				return levels;


			}else if (levelRoller>=50){

				maxlevel= lowestPlayer+10;
				minlevel= lowestPlayer-1;

				if (minlevel<1)
				{
					minlevel = 1;
				}

				int levels = Utils.randInt(minlevel, maxlevel);
				return levels;
			}


		}

		else if(players.length==1){

			maxlevel= players[0].getLevel()+7;
			minlevel= players[0].getLevel()-1;

			if (minlevel<1)
			{
				minlevel = 1;
			}

			int levels = Utils.randInt(minlevel, maxlevel);
			return levels;
		}

		return 1;


	}




	public static void spawnEntity(Entity entity, Location loc)
	{
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
	}



	public static EntitySkeleton spawnSkeleton (net.minecraft.server.v1_8_R3.World nmsWorld,int spawnLevel) {


		String MobName = "de.hulaa.CustomMobs3.CustomEntitySkeleton"+spawnLevel;

		try {
			Class mobClass = Class.forName(MobName);
			Constructor mobConstructor = mobClass.getConstructor(net.minecraft.server.v1_8_R3.World.class);
			Object mob = mobConstructor.newInstance(nmsWorld);

			return (EntitySkeleton) mob;

		}catch (InstantiationException x) {
			x.printStackTrace();
		} catch (InvocationTargetException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException x) {
			x.printStackTrace();
		}
		return null;


	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
	
	
	

			
			
			
			
			
			
			
			
			

