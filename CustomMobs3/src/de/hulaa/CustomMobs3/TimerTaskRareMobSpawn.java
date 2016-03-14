package de.hulaa.CustomMobs3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.TimerTask;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.World;

public class TimerTaskRareMobSpawn extends TimerTask {

	public static ArrayList<Class<? extends EntityLiving>> rareMobList = new ArrayList<Class<?extends EntityLiving>>();
	
	
	@Override
	public void run() {

		//choose Player
		ArrayList<Player> players = TimerTaskGetOnlinePlayers.players;
		if(players.size()>0 && rareMobList.size()>0){
			
			int playerIndex = Utils.randInt(0, players.size()-1);
			Player spawnPlayer = players.get(playerIndex);
			
			
			
			//choose Location
			World world = ((CraftWorld) spawnPlayer.getWorld()).getHandle();
			Location spawnLoc = AdditionalMobSpawner.getSpawnLocation(spawnPlayer);
			
			
			
			//choose Level
			int level = AdditionalMobSpawner.getSpawnLevel(spawnPlayer.getLevel());
			int mobRoll = Utils.randInt(0, RareMobs.values().length-1);
			
			RareMobs selectedMob = RareMobs.values()[mobRoll];
			
			
			
			
			for(Class c: selectedMob.members){

				try {
					Constructor mobConstructor = c.getConstructor(net.minecraft.server.v1_9_R1.World.class,Integer.class);
					Object mob = mobConstructor.newInstance(world,level);
					
					AdditionalMobSpawner.spawnEntity((Entity) mob, spawnLoc);
					
					
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		
	}

}
