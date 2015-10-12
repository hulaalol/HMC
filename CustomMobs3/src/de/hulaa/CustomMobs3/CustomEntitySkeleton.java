package de.hulaa.CustomMobs3;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

import java.lang.Integer;

	

	
	public class CustomEntitySkeleton extends EntitySkeleton {
		

	
		

		public CustomEntitySkeleton(World world) {
		super(world);
		
		
		Double level = ((this.getAttributeInstance(GenericAttributes.maxHealth).getValue()-5D)/1.5D);
		Integer intLevel = level.intValue();

		
		String levelString = String.format("%d", (intLevel));
		super.setCustomName("StandardSkeleton <"+levelString+">");
		
		
		//EQUIPMENT PROCESS
				ItemStack[] equipment = EquipmentGenerator.generateEquipment(intLevel);
				for (int i = 0; i<5; i++){
					
					if (equipment[i]!=null)
					{
						net.minecraft.server.v1_8_R3.ItemStack equip = CraftItemStack.asNMSCopy(equipment[i]);
						super.setEquipment(i,equip );
					}else{
						// equip nothing
					}
				}
		

		super.dropEquipment(true, 1);
		
		
		
		
		//lore
        //org.bukkit.inventory.ItemStack testSwordItem = CraftItemStack.asCraftMirror(weapon); 
//        ItemMeta testSwordMeta = bootsItem.getItemMeta(); <-- hier aufgehört!!
//        
//        if (testSwordMeta.hasLore()){
//        	
//        }else{
//        	testSwordMeta.setDisplayName(ChatColor.DARK_AQUA + "Testing Item Name");
//	        List<String> lore = testSwordMeta.getLore();
//	        lore.add(ChatColor.AQUA + "Testing Item Lore");
//	        testSwordMeta.setLore(lore);
//	        testSwordItem.setItemMeta(testSwordMeta);
//        	
//        }



}

		@Override
		protected void initAttributes() {
			super.initAttributes();

			int level = AdditionalMobSpawner.spawnLevel();

			double attackDamage= (2D+(0.5D*level));
			double maxHealth = CustomEntitySkeletonAttributeSetter.setSkeletonHealth(level);
			double mvmnt = (0.1D*(level/8)+0.1D);
			double followRange = (150D+(level*3));
			double KnockbackResist = 0.1D+(level/30);
			


			this.getAttributeInstance(GenericAttributes.maxHealth).setValue(maxHealth);
			this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(attackDamage);
			this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(mvmnt);
			this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(followRange);
			this.getAttributeInstance(GenericAttributes.c).setValue(KnockbackResist);
		}
		
		public double getLevel(){
			return ((this.getAttributeInstance(GenericAttributes.maxHealth).getValue()-5D)/1.5D);
		}
	

	
}