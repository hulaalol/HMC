package de.hulaa.CustomMobs;



import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;



public interface EquipmentDataBase {
	
	
	static ItemStack[] BOOTS = {
		new ItemStack(Material.LEATHER_BOOTS, 1),
		new ItemStack(Material.CHAINMAIL_BOOTS,1),			
		new ItemStack(Material.IRON_BOOTS,1),
		new ItemStack(Material.GOLD_BOOTS,1),	
		new ItemStack(Material.DIAMOND_BOOTS,1)
		};
	
	static ItemStack[] LEGGINGS = {
		new ItemStack(Material.LEATHER_LEGGINGS, 1),
		new ItemStack(Material.CHAINMAIL_LEGGINGS,1),			
		new ItemStack(Material.IRON_LEGGINGS,1),
		new ItemStack(Material.GOLD_LEGGINGS,1),
		new ItemStack(Material.DIAMOND_LEGGINGS,1)
		};
	
	static ItemStack[] CHESTPLATE = {
		new ItemStack(Material.LEATHER_CHESTPLATE, 1),
		new ItemStack(Material.CHAINMAIL_CHESTPLATE,1),			
		new ItemStack(Material.IRON_CHESTPLATE,1),
		new ItemStack(Material.GOLD_CHESTPLATE,1),
		new ItemStack(Material.DIAMOND_CHESTPLATE,1)
		
		};
	
	static ItemStack[] HELMET = {
		new ItemStack(Material.LEATHER_HELMET, 1),
		new ItemStack(Material.CHAINMAIL_HELMET,1),			
		new ItemStack(Material.IRON_HELMET,1),
		new ItemStack(Material.GOLD_HELMET,1),	
		new ItemStack(Material.DIAMOND_HELMET,1)
		};
	

	static ItemStack[] WEAPONS = {
		
		new ItemStack(Material.BOW, 1),
		
		new ItemStack(Material.STONE_AXE, 1),
		new ItemStack(Material.STONE_SWORD,1),			
		new ItemStack(Material.STONE_HOE,1),		
		new ItemStack(Material.STONE_PICKAXE,1),
		new ItemStack(Material.STONE_SPADE,1),
		
		new ItemStack(Material.WOOD_AXE, 1),
		new ItemStack(Material.WOOD_SWORD,1),			
		new ItemStack(Material.WOOD_HOE,1),		
		new ItemStack(Material.WOOD_PICKAXE,1),	
		new ItemStack(Material.WOOD_SPADE,1),
		
		new ItemStack(Material.IRON_AXE, 1),
		new ItemStack(Material.IRON_SWORD,1),			
		new ItemStack(Material.IRON_HOE,1),		
		new ItemStack(Material.IRON_PICKAXE,1),	
		new ItemStack(Material.IRON_SPADE,1), //15
		
		new ItemStack(Material.GOLD_AXE, 1),
		new ItemStack(Material.GOLD_SWORD,1), //17			
		new ItemStack(Material.GOLD_HOE,1),		
		new ItemStack(Material.GOLD_PICKAXE,1),	
		new ItemStack(Material.GOLD_SPADE,1),
		
		new ItemStack(Material.DIAMOND_AXE, 1),
		new ItemStack(Material.DIAMOND_SWORD,1),			
		new ItemStack(Material.DIAMOND_HOE,1),		
		new ItemStack(Material.DIAMOND_PICKAXE,1),	
		new ItemStack(Material.DIAMOND_SPADE,1)};
		
		
		static ItemStack[] OFFHAND = {
				new ItemStack(Material.SHIELD,1)
		};
	
		
		static ItemStack[] HORSE_EQUIP = {
				new ItemStack(Material.SADDLE,1),			//0
				new ItemStack(Material.IRON_BARDING,1),		//1
				new ItemStack(Material.GOLD_BARDING,1),		//2
				new ItemStack(Material.DIAMOND_BARDING,1)	//3
		};
		
	static double[] WEAPON_DAMAGE = {0,						//BOW
									4,5,0,3,2,				//STONE
									3,4,0,2,1,				//WOOD
									5,6,0,4,3,				//IRON
									3,4,0,2,1,				//GOLD
									6,7,0,5,4};				//DIAMOND
	
	
}

	
	
	
	
	


