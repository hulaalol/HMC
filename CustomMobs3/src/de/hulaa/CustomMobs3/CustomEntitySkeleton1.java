package de.hulaa.CustomMobs3;


import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

	

	
	public class CustomEntitySkeleton1 extends EntitySkeleton { 
		

		public double getLevel(){
			return 0.5*this.getAttributeInstance(GenericAttributes.maxHealth).getValue();
		}
		

		public CustomEntitySkeleton1(World world) {
		super(world);


		super.setCustomName("CustomSkeleton <1>");

		
		//EQUIPMENT PROCESS
		ItemStack[] equipment = EquipmentGenerator.generateEquipment(1);
		for (int i = 0; i<5; i++){
			
			if (equipment[i]!=null)
			{
				net.minecraft.server.v1_8_R3.ItemStack equip = CraftItemStack.asNMSCopy(equipment[i]);
				super.setEquipment(i,equip );
			}else{
				// equip nothing
			}
		}
		
		
		
		super.dropEquipment(false, 1000);
		

		}

	@Override
	protected void initAttributes() {
		super.initAttributes();
		

		  	this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
		  	this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(3.0D);
		  	this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.1D);
	}
	

	
}