package de.hulaa.CustomMobs3;

import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class CustomEntityExperienceOrb extends EntityExperienceOrb{
	
	
	private int mobLevel;
	private int killerLevel;
	private int killerCollectorDifference;

	public CustomEntityExperienceOrb(World world) {
		super(world);
		//Bukkit.getServer().broadcastMessage("[BETA] Custom XP Orb spawned!");
		
	}
	
	public CustomEntityExperienceOrb(World world, int mobLevel){
		super(world);
		this.mobLevel = mobLevel;
	}
	
	public CustomEntityExperienceOrb(World world, int mobLevel, int killerLevel)
	{
		super(world);
		this.mobLevel = mobLevel; 
		this.killerLevel = killerLevel;
		this.killerCollectorDifference = 0;
	}
	
	
	@Override
    public void d(EntityHuman entityhuman) {
        if (!this.world.isClientSide) {
            if (this.c == 0 && entityhuman.bp == 0) {
                entityhuman.bp = 2;
                this.world.makeSound(entityhuman, "random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
                entityhuman.receive(this, 1);
                
                
                
                org.bukkit.entity.Player collectingPlayer = (Player) entityhuman.getBukkitEntity();
                int collectingPlayerLevel = collectingPlayer.getLevel();
                
                this.killerCollectorDifference = (this.killerLevel - collectingPlayerLevel);
                
                if(collectingPlayerLevel <50 && (killerCollectorDifference<10)){
                
                float totalExpF = (float) entityhuman.expTotal;
                float mobExp = getOrbPercentageValue(collectingPlayerLevel, this.mobLevel);

                collectingPlayer.setExp(entityhuman.exp + mobExp);
                
                if(mobExp!=0.0F){
                collectingPlayer.sendMessage("�a+"+ String.format("%.8f", (mobExp*100)) +"% XP");
                }
            	

            	entityhuman.expTotal = Math.round(totalExpF);

                for(totalExpF+=mobExp; entityhuman.exp>=1.0F; entityhuman.exp/=(float)entityhuman.getExpToLevel()){
                	entityhuman.exp = (entityhuman.exp - 1.0F) * (float) entityhuman.getExpToLevel();
                	entityhuman.expTotal = Math.round(totalExpF);
                	entityhuman.levelDown(1);
                }

                }
  
                	this.die();	
            }
        }
    }
	
	public static int getOrbQuantity(int playerLevel, int mobLevel){
		
		int difference = playerLevel-mobLevel;
		double dice = Math.random();
		
		
		switch(difference){
		
		case -10:
		case -9:
		case -8:
		return 3;
		
		case -7:
		return 2;
		
				
		case -6:
		if (dice > 0.2D){
			return 2;
		}else{
			return 1;
		}
			
		case -5:
		if(dice > 0.5D){
			return 2;
		}else{
			return 1;
		}
		
		case -4:
		if(dice > 0.7D){
			return 2;
		}else{
			return 1;
		}
		
		default:
		return 1;
		
		}
		
	}


	public static float getOrbPercentageValue(int playerLevel, int mobLevel) {
		// =(EXP(-0,061*level))*0,106
		Double valueXP = (Math.pow(Math.E, ((-0.061D)*playerLevel)))*0.0106D;
		float fvalueXP = valueXP.floatValue();
		
		int difference = playerLevel-mobLevel;
		
		if(difference<=0){   //same level or higher
			
			switch(difference){
			
			case 0:
			return fvalueXP;
			case -1:
			return (fvalueXP*1.05F);
			case -2:
			return (fvalueXP*1.15F);
			case -3:
			return (fvalueXP*1.30F);
			case -4:
			return (fvalueXP*1.45F);
			case -5:
			return (fvalueXP*1.60F);
			case -6:
			return (fvalueXP*1.70F);
			case -7:
			return (fvalueXP*1.85F);
			case -8:
			return (fvalueXP*1.95F);
			default:
			return (fvalueXP*2.0F);
			}
			
		}else{ //lower level
			
			switch(difference){
			case 4:
			return (fvalueXP*0.1F);
			case 3:
			return (fvalueXP*0.25F);
			case 2:
			return (fvalueXP*0.5F);
			case 1:
			return (fvalueXP*0.75F);
			default:
			return 0.0F;
			}
			
		}

	}
	
	public static int getOrbValue(int i){
		return 1;
	}
	
	public static Location orbLoc(Location loc, int splashRadius){
		
		double[] locXZ ={loc.getX(), loc.getZ()};

		for(int i=0; i<2; i++){
		int sign = Utils.randInt(1, 2);
		locXZ[i] += (Math.pow(-1, sign))*(splashRadius*Math.random());
		}
		
		loc.setX(locXZ[0]);
		loc.setZ(locXZ[1]);
		loc.setY(loc.getY()+1.8D);

		return loc;
	}
	
	
	
	

}
