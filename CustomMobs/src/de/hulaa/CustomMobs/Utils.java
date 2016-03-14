package de.hulaa.CustomMobs;

import java.util.Random;

import org.bukkit.Location;

public class Utils {
	
	// random Number generator
	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
	
	
	public static String announceLocation(Location loc){
		
		String locationXYZ = "X: "
		+ String.format("%d", (int)loc.getX()) 
		+" Y: "
		+ String.format("%d", (int)loc.getY())
		+" Z: "
		+ String.format("%d", (int)loc.getZ()); 
		
		return locationXYZ;
	}

	
	
	
	
	
	



}

