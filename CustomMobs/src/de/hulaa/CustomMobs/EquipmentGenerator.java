package de.hulaa.CustomMobs;

import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EnumItemSlot;
import net.minecraft.server.v1_9_R1.GenericAttributes;



public class EquipmentGenerator implements EquipmentDataBase {
	
	public enum EquipType{
		ARMOR,
		WEAPON;
	}
	
	
	public static ItemStack[] generateEquipment (int moblevel){
		
			ItemStack[] equipment = new ItemStack[6];
			
			
			//weapon
			if(RollSlot(moblevel,EquipType.WEAPON)){
				int weaponIndex = Utils.randInt(0, EquipmentDataBase.WEAPONS.length-1);
				
				if(GenericAttributesFunction.mobAd(moblevel)>EquipmentDataBase.WEAPON_DAMAGE[weaponIndex]){
					equipment[0] = EquipmentDataBase.WEAPONS[weaponIndex];	
				}
			}
			
			//offhand
			if(RollSlot(moblevel,EquipType.WEAPON)){
				
				//only half of the Mobs will actually get a offhand-Item!
				if(Math.random()>0.5){
					
					//30% chance to dual wield
					if(Math.random()>0.7){
						int weaponIndex = Utils.randInt(0, EquipmentDataBase.WEAPONS.length-1);
						
						if(GenericAttributesFunction.mobAd(moblevel)>EquipmentDataBase.WEAPON_DAMAGE[weaponIndex]){
							equipment[5] = EquipmentDataBase.WEAPONS[weaponIndex];	
						}
					//70% chance to get a Shield!
					}else{
						int offHandIndex = Utils.randInt(0, EquipmentDataBase.OFFHAND.length-1);
						equipment[5] = EquipmentDataBase.OFFHAND[offHandIndex];
					}
				}
			}
			

			//boots
			if (RollSlot(moblevel,EquipType.ARMOR))
			{
				int bootsIndex = Utils.randInt(0, EquipmentDataBase.BOOTS.length-1);
				equipment[1] = EquipmentDataBase.BOOTS[bootsIndex];
			}else{
				equipment[1] = null;
			}
			
			//leggings
			if (RollSlot(moblevel,EquipType.ARMOR))
			{
				int leggingsIndex = Utils.randInt(0, EquipmentDataBase.LEGGINGS.length-1);
				equipment[2] = EquipmentDataBase.LEGGINGS[leggingsIndex];
			}else{
				equipment[2] = null;
			}
			
			//chestplate
			if (RollSlot(moblevel,EquipType.ARMOR))
			{
				int chestplateIndex = Utils.randInt(0, EquipmentDataBase.CHESTPLATE.length-1);
				equipment[3] = EquipmentDataBase.CHESTPLATE[chestplateIndex];
			}else{
				equipment[3] = null;
			}
			
			//helmet
			if (RollSlot(moblevel,EquipType.ARMOR))
			{
				int chestplateIndex = Utils.randInt(0, EquipmentDataBase.HELMET.length-1);
				equipment[4] = EquipmentDataBase.HELMET[chestplateIndex];
			}else{
				equipment[4] = null;
			}
			
			return equipment;
	
			
		}

	public static boolean RollSlot(int moblevel, EquipType type){
		
		double dice = Math.random();
		double probability;
		
		if(type==EquipType.ARMOR){
			probability = 0.05+(moblevel/100);
		}else if (type == EquipType.WEAPON){
			probability = 0.2+(moblevel/100);
		}else{
			probability = 0.0D;
		}

		if(dice+probability>1){
			return true;
		}else{
			return false;
		}
	}


	public static void adjustAD(EntityLiving mob, ItemStack weapon){
		
		for(int i=0; i>EquipmentDataBase.WEAPONS.length; i++){
			
			if (weapon.equals(EquipmentDataBase.WEAPONS[i])){

				double ad = mob.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();

				mob.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(ad-EquipmentDataBase.WEAPON_DAMAGE[i]);
				
				break;
			}
		}
	}
	
	
	public static void equipMob(EntityInsentient entity, ItemStack[] equipment){
		entity.setSlot(EnumItemSlot.MAINHAND, null);
		entity.setSlot(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(equipment[0]));
		
		entity.setSlot(EnumItemSlot.OFFHAND, null);
		entity.setSlot(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(equipment[5]));
		
		entity.setSlot(EnumItemSlot.FEET, null);
		entity.setSlot(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(equipment[1]));
		
		entity.setSlot(EnumItemSlot.LEGS, null);
		entity.setSlot(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(equipment[2]));
		
		entity.setSlot(EnumItemSlot.CHEST, null);
		entity.setSlot(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(equipment[3]));
		
		entity.setSlot(EnumItemSlot.HEAD, null);
		entity.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(equipment[4]));
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
