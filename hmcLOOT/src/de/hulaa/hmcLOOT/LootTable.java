package de.hulaa.hmcLOOT;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface LootTable {
	
	static ItemStack[] ARMOR = {
	new ItemStack(Material.LEATHER_HELMET,1),			//0
	new ItemStack(Material.LEATHER_CHESTPLATE,1),		//1
	new ItemStack(Material.LEATHER_LEGGINGS,1),			//2
	new ItemStack(Material.LEATHER_BOOTS,1)				//3
	};
	
	
	static ItemStack[] WEAPON = {
	new ItemStack(Material.LEATHER_HELMET,1),
	new ItemStack(Material.LEATHER_CHESTPLATE,1),
	new ItemStack(Material.LEATHER_LEGGINGS,1),
	new ItemStack(Material.LEATHER_BOOTS,1)
	};
	
	
	
	static ItemStack[] LOOT = {
	new ItemStack(Material.LEATHER_HELMET,1),
	new ItemStack(Material.LEATHER_CHESTPLATE,1),
	new ItemStack(Material.LEATHER_LEGGINGS,1),
	new ItemStack(Material.LEATHER_BOOTS,1)
	};
	
	
	static ItemStack[] MONEY = {
	new ItemStack(Material.LEATHER_HELMET,1),
	new ItemStack(Material.LEATHER_CHESTPLATE,1),
	new ItemStack(Material.LEATHER_LEGGINGS,1),
	new ItemStack(Material.LEATHER_BOOTS,1)
	};

}
