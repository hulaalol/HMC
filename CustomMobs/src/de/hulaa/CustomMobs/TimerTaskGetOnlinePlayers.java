package de.hulaa.CustomMobs;

import java.util.ArrayList;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TimerTaskGetOnlinePlayers extends TimerTask {

	public static ArrayList<Player> players = new ArrayList<Player>();

	@Override
	public void run() {
		
	players.clear();	
		
	Bukkit.getOnlinePlayers().stream()
	.filter(p -> p.getWorld().getEnvironment()
			.equals(org.bukkit.World.Environment.NORMAL))
	.forEach(p -> players.add(p));	
	
	
	Bukkit.getServer().broadcastMessage("[BETA] Updated Playerlist : "+players.size());
	}

	public TimerTaskGetOnlinePlayers() {
		super();
	}

}
