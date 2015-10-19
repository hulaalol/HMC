package de.hulaa.CustomMobs3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;

import org.bukkit.inventory.ItemStack;



public class EquipmentGenerator implements EquipmentDataBase {
	
	
	
	
	public static ItemStack[] generateEquipment (int moblevel){
		
			//bessere implementierung RollSlot liefet ein Array aus 1/0 --> generateEquipment iteriert über das Array??

			ItemStack[] equipment = new ItemStack[5];
			
			
			//weapon

				int weaponIndex = Utils.randInt(0, EquipmentDataBase.WEAPONS.length-1);
				
				if(GenericAttributesFunction.mobAd(moblevel)>EquipmentDataBase.WEAPON_DAMAGE[weaponIndex]){
				equipment[0] = EquipmentDataBase.WEAPONS[weaponIndex];	
				}
				
			

			//boots
			if (RollSlot(moblevel)==1)
			{
				int bootsIndex = Utils.randInt(0, EquipmentDataBase.BOOTS.length-1);
				equipment[1] = EquipmentDataBase.BOOTS[bootsIndex];
			}else{
				equipment[1] = null;
			}
			
			//leggings
			if (RollSlot(moblevel)==1)
			{
				int leggingsIndex = Utils.randInt(0, EquipmentDataBase.LEGGINGS.length-1);
				equipment[2] = EquipmentDataBase.LEGGINGS[leggingsIndex];
			}else{
				equipment[2] = null;
			}
			
			//chestplate
			if (RollSlot(moblevel)==1)
			{
				int chestplateIndex = Utils.randInt(0, EquipmentDataBase.CHESTPLATE.length-1);
				equipment[3] = EquipmentDataBase.CHESTPLATE[chestplateIndex];
			}else{
				equipment[3] = null;
			}
			
			//helmet
			if (RollSlot(moblevel)==1)
			{
				int chestplateIndex = Utils.randInt(0, EquipmentDataBase.HELMET.length-1);
				equipment[4] = EquipmentDataBase.HELMET[chestplateIndex];
			}else{
				equipment[4] = null;
			}
			
			return equipment;
	
			
		}
	

	
	public static byte RollSlot (int moblevel) {
		
		int roll = Utils.randInt(0, 100);
		
		if (moblevel <=10 && roll>90)
		{
			return 1;
		}else if (moblevel>=11 && /*moblevel <20 && */ roll>70)
		{
			return 1;
		}else{
			return 0;
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
	
	
	
	
	
	
	
	
	
	
	
	

}
