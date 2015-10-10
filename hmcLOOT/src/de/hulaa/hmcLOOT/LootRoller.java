package de.hulaa.hmcLOOT;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;



public class LootRoller implements LootTable {
	
	
	public static ItemStack giveArmorLoot(int PlayerLevel){
		
		int ArmorIndex = (LootTable.ARMOR.length)-1;
		
		if (PlayerLevel<100)
		{
			int LootID = randInt(0,ArmorIndex);
			return LootTable.ARMOR[LootID];
		}
		return null;
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
	

}
